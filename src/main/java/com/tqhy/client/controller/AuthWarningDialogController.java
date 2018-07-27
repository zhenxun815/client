package com.tqhy.client.controller;

import com.tqhy.client.view.FxmlUtils;
import com.tqhy.client.view.ViewsUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author Yiheng
 * @create 2018/6/27
 * @since 1.0.0
 */
public class AuthWarningDialogController extends BaseDialogController {

    @FXML
    Label lb_auth_warning;

    public AuthWarningDialogController() {
        FxmlUtils.load("/dialog/auth_warning/auth_warning.fxml", this);
        initDialog();
        lb_auth_warning.setText("请求授权提示...请求授权提示...请求授权提示...请求授权提示...请求授权提示...");
    }

    public String show(Stage primaryStage) {
        primaryStage.hide();
        Optional<ButtonType> cmd = showAtCenter();
        switch (cmd.get().getButtonData()) {
            case YES:
                primaryStage.close();
                System.exit(0);
                break;
            case CANCEL_CLOSE:
                primaryStage.show();
                break;
        }
        return ViewsUtils.SHOW_VIEW_TAG;
    }
}
