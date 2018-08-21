package com.tqhy.client.controller;

import com.tqhy.client.listener.OnWebViewShowingListener;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.model.ClientMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.ApiBean;
import com.tqhy.client.view.FxmlUtils;
import io.reactivex.schedulers.Schedulers;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class AiWarningDialogController extends AnchorPane {

    private Logger logger = LoggerFactory.getLogger(AiWarningDialogController.class);
    private ObjectProperty<AiResult> aiResult = new SimpleObjectProperty<>();
    private BooleanProperty dialogShouldShowingFlag = new SimpleBooleanProperty();
    private OnWebViewShowingListener webViewShowingListener;
    private Stage stage;

    @FXML
    Label lb_warning_content;
    @FXML
    Button bt_confirm;
    @FXML
    Button bt_exclude;
    @FXML
    Button bt_close;

    //1已警告,0未警告
    Integer warning_flag = 1;
    //0警告未解除,1继续警告
    Integer ai_warning = null;
    //0非误报,1误报
    Integer error_flag = null;

    public AiWarningDialogController() {
        FxmlUtils.load("/dialog/ai_warning/ai_warning.fxml", this);
        Scene scene = new Scene(this);
        scene.setFill(Color.TRANSPARENT);
        stage = new Stage();
        stage.setAlwaysOnTop(true);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        bt_confirm.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            logger.info("warning confirm clicked...");

            WebViewController web = new WebViewController();
            webViewShowingListener.bindShowingProperty(web);
            closeDialog();
            web.showTqWeb(getAiResult().getAiDrId(), Network.AI_PROMPT_PAGE);
        });

        bt_exclude.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            logger.info("exclude confirm clicked...");
            error_flag = 1;
            ai_warning = 0;
            closeDialog();
            postAiWarningBack(ai_warning, error_flag, warning_flag);
        });

        bt_close.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            logger.info("close confirm clicked...");
            closeDialog();
        });


        // stage.getScene().setUserAgentStylesheet();
        stage.setOnShowing(event -> {
            TranslateTransition transTrans = new TranslateTransition(Duration.millis(500), this);
            transTrans.setFromX(300);
            transTrans.setFromY(0);
            transTrans.setToX(0);
            transTrans.setToY(0);
            transTrans.play();
        });
    }

    public void show(Stage primaryStage) {

        lb_warning_content.setText(getAiResult().getAiImgResult());

        stage.setX(primaryStage.getX() - 300);
        stage.setY(primaryStage.getY());
        stage.show();
        primaryStage.xProperty().addListener((observable, oldValue, newValue) -> {
            stage.setX(newValue.doubleValue() - 300);
        });
        primaryStage.yProperty().addListener((observable, oldValue, newValue) -> {
            stage.setY(newValue.doubleValue());
        });
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


    public void closeDialog() {
        logger.info("closeDialog() invoked...");
        if (stage.isShowing()) {
            Platform.runLater(() -> {
                stage.hide();
            });
        }
    }

    public void setWebViewShowingListener(OnWebViewShowingListener webViewShowingListener) {
        this.webViewShowingListener = webViewShowingListener;
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
