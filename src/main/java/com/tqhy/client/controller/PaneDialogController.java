package com.tqhy.client.controller;

import com.tqhy.client.model.bean.AiResult;
import com.tqhy.client.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class PaneDialogController extends BaseDialogController {

    private AiResult result;
    @FXML
    Label lb_result;


    public PaneDialogController(AiResult result) {
        this.result = result;
        FxmlUtils.load("/dialog/pane_dialog/pane_dialog.fxml", this);
        initDialog();
        //initButtonType();
        lb_result.setText("活动性肺结核");
    }

    /**
     * 显示弹窗
     *
     * @param primaryStage 悬浮窗口
     */
    public void show(Stage primaryStage) {
        primaryStage.hide();
        Optional<ButtonType> cmd = showAtRightBottom();
        switch (cmd.get().getButtonData()) {
            case YES:
                WebViewDialogController web = new WebViewDialogController();
                web.showWeb("http://www.enlightmentcloud.com/");
                break;
            case NO:
                //todo 反馈给后台
                System.out.println("exclude clicked....");
                break;
            case CANCEL_CLOSE:
                break;
        }
        primaryStage.getScene().getRoot().setStyle("-fx-background-color: dimgray;");
        primaryStage.show();
    }

}
