package com.tqhy.client.controller;

import com.tqhy.client.listener.OnWebViewShowingListener;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.model.ClientMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.ApiBean;
import com.tqhy.client.network.app.JavaAppAiWarning;
import com.tqhy.client.view.FxmlUtils;
import io.reactivex.schedulers.Schedulers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class AiWarningDialogController extends BaseDialogController {

    private Logger logger = LoggerFactory.getLogger(AiWarningDialogController.class);
    private ObjectProperty<AiResult> aiResult = new SimpleObjectProperty<>();
    private BooleanProperty dialogShouldShowingFlag = new SimpleBooleanProperty();
    private JavaAppAiWarning javaAppAiWarning;

    public AiWarningDialogController() {
        FxmlUtils.load("/dialog/ai_warning/ai_warning.fxml", this);
        initDialog();
    }

    public void show(Stage primaryStage, OnWebViewShowingListener webViewShowingListener) {
        //primaryStage.setAlwaysOnTop(false);
        javaAppAiWarning = new JavaAppAiWarning(getAiResult(), webViewShowingListener);
        WebViewController webViewController = new WebViewController(javaAppAiWarning);
        setDialogShouldShowingFlag(true);
        //webViewController.showWeb(Network.BASE_URL + "html/ai-warning.html", WebViewController.WEB_TYPE_AI_WARNING);
        webViewController.showLocalWeb(primaryStage, "/html/ai-warning.html");
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

    @Override
    public void closeDialog() {
        super.closeDialog();
        javaAppAiWarning.closeWindow();
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


    public boolean getDialogShouldShowingFlag() {
        return dialogShouldShowingFlag.get();
    }

    public BooleanProperty dialogShouldShowingFlagProperty() {
        return dialogShouldShowingFlag;
    }

    public void setDialogShouldShowingFlag(boolean dialogShouldShowingFlag) {
        this.dialogShouldShowingFlag.set(dialogShouldShowingFlag);
    }
}
