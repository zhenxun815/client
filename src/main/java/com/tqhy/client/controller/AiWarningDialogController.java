package com.tqhy.client.controller;

import com.tqhy.client.listener.OnWebViewShowingListener;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.model.ClientMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.ApiBean;
import com.tqhy.client.view.FxmlUtils;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private Logger logger = LoggerFactory.getLogger(AiWarningDialogController.class);
    private ObjectProperty<AiResult> aiResult = new SimpleObjectProperty<>();
    @FXML
    Label lb_ai_warning;


    public AiWarningDialogController() {

        FxmlUtils.load("/dialog/ai_warning/ai_warning.fxml", this);
        initDialog();
    }

    /**
     * 显示弹窗
     *
     * @param primaryStage 悬浮窗口
     */
    public void show(Stage primaryStage, OnWebViewShowingListener webViewShowingListener) {
        lb_ai_warning.setText(null == this.aiResult.get() ? "null" : this.aiResult.get().getAiImgResult());

        primaryStage.setAlwaysOnTop(false);


        //切忌通过调用primaryStage.hide();来实现弹窗置顶!!
        Optional<ButtonType> cmd = showAtRightBottom();

        primaryStage.getScene().getRoot().setStyle("-fx-background-color: dimgray;");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        //1已警告,0未警告
        Integer warning_flag = 1;
        //0警告未解除,1继续警告
        Integer ai_warning = null;
        //0非误报,1误报
        Integer error_flag = null;
        //医生是否确认
        Integer operation = null;

        switch (cmd.get().getButtonData()) {
            case YES: //进入webView详情页

                logger.info("dialog detail clicked....");
                WebViewController web = new WebViewController();
                webViewShowingListener.bindShowingProperty(web);
                web.showTqWeb(this.aiResult.get().getAiDrId(), Network.AI_PROMPT_PAGE);
                ai_warning = 1;
                postAiWarningBack(ai_warning, error_flag, warning_flag);

                break;
            case NO:
                logger.info("dialog exclude clicked....");
                error_flag = 1;
                ai_warning = 0;
                postAiWarningBack(ai_warning, error_flag, warning_flag);

                break;
            case CANCEL_CLOSE:
                logger.info("dialog close invoked..");
                ai_warning = 1;
                postAiWarningBack(ai_warning, error_flag, warning_flag);
                break;
        }
    }

    /**
     * 向后台反馈医生"确认"还是"排除"ai提示的操作
     * 参数operation的值与数据控表中error_flag值相对应,
     * 即operation==1,则设定error_flag=1,即医生点击了排除;
     * operation==0,则设定error_flag=0,医生点击了确认.
     *
     * @param operation 0:确认,1:排除
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
        logger.info("post warning back: " + warningBack);
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


    public AiResult getAiResult() {
        return aiResult.get();
    }

    public ObjectProperty<AiResult> aiResultProperty() {
        return aiResult;
    }

    public void setAiResult(AiResult aiResult) {
        this.aiResult.set(aiResult);
    }
}
