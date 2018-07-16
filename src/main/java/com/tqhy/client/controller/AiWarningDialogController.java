package com.tqhy.client.controller;

import com.tqhy.client.listener.OnWebViewShowingListener;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.model.ClientMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.ApiBean;
import com.tqhy.client.utils.FxmlUtils;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class AiWarningDialogController extends BaseDialogController {

    private AiResult result;
    private Logger logger = LoggerFactory.getLogger(AiWarningDialogController.class);
    @FXML
    Label lb_ai_warning;


    public AiWarningDialogController(AiResult result) {
        this.result = result;
        FxmlUtils.load("/dialog/ai_warning/ai_warning.fxml", this);
        initDialog();
        //initButtonType();
        lb_ai_warning.setText(null == this.result.getAiImgResult() ? "null" : this.result.getAiImgResult());
    }

    /**
     * 显示弹窗
     *
     * @param primaryStage 悬浮窗口
     */
    public void show(Stage primaryStage, OnWebViewShowingListener webViewShowedListener) {
        primaryStage.hide();
        Optional<ButtonType> cmd = showAtRightBottom();
        primaryStage.getScene().getRoot().setStyle("-fx-background-color: dimgray;");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        //1警告,0解除警告
        Integer warning_flag = null;
        //0未警告,1警告了
        Integer ai_warning = null;
        //0误报,1非误报
        Integer error_flag = null;
        //医生是否确认
        Integer operation = null;
        switch (cmd.get().getButtonData()) {
            case YES:
                WebViewDialogController web = new WebViewDialogController();
                //web.showWeb("http://192.168.1.214:8080/ai/helper/index?id=" + this.result.getAiDrId()+"&pageName=ai_prompt");
                web.showTqWeb(this.result.getAiDrId(), Network.AI_PROMPT_PAGE);
                webViewShowedListener.shouldJnaFetchData(web);
                warning_flag = 0;
                ai_warning = 1;
                postAiWarningBack(ai_warning, error_flag, warning_flag);
                break;
            case NO:
                logger.info("exclude clicked....");
                error_flag = 0;
                warning_flag = 0;
                operation = 0;
                postAiWarningBack(ai_warning, error_flag, warning_flag);
                postDocConfirm(operation);
                break;
            case CANCEL_CLOSE:
                warning_flag = 1;
                postAiWarningBack(ai_warning, error_flag, warning_flag);
                break;
        }
    }

    /**
     * 向后台反馈医生"确认"还是"排除"ai提示的操作
     *
     * @param operation 1:确认,0:排除
     */
    private void postDocConfirm(Integer operation) {
        ApiBean<ClientMsg> apiBean = new ApiBean<>();
        ClientMsg docConfirm = new ClientMsg();
        docConfirm.setAiDrId(Network.currentId);
        docConfirm.setOperation(operation);
        apiBean.setBean(docConfirm);
        Network.getAiHelperApi()
                .postHistory(apiBean)
                .map(body -> {
                    String json = body.string();
                    logger.info("AiWarningDialogController postDocConfirm recieve json:" + json);
                    return json;
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(json -> {
                    logger.info(json);
                });
    }

    /**
     * 向后台反馈是否接收到弹窗信息及弹框相关操作
     *
     * @param aiWarning
     * @param errorFlag
     * @param warningFlag
     */
    private void postAiWarningBack(Integer aiWarning, Integer errorFlag, Integer warningFlag) {
        ApiBean<ClientMsg> apiBean = new ApiBean<>();
        ClientMsg warningBack = new ClientMsg();
        warningBack.setOperationIp(Network.getLocalIp());
        warningBack.setAiDrId(Network.currentId);
        warningBack.setWarningBack(aiWarning, errorFlag, warningFlag);
        logger.info("post warning back: "+warningBack);
        apiBean.setBean(warningBack);

        Network.getAiHelperApi()
                .postAiWarningBack(apiBean)
                .map(body -> {
                    String json = body.string();
                    logger.info("AiWarningDialogController postAiWarningBack recieve json:" + json);
                    return json;
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(json -> {
                    logger.info(json);
                });
    }
}
