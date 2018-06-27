package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class LandDialogController extends BaseDialogController {

    @FXML
    TextField user_name;
    @FXML
    PasswordField user_pwd;

    public LandDialogController() {
        FxmlUtils.load("/dialog/login/login.fxml", this);
        initDialog();
    }

    public void show() {
        Optional<ButtonType> cmd = showAtCenter();
        switch (cmd.get().getButtonData()) {
            case OK_DONE:
                String name = user_name.getText();
                String pwd = user_pwd.getText();
                System.out.println("land ok..name: " + name + " pwd: " + pwd);
                break;
            case CANCEL_CLOSE:
                System.out.println("close cancle..");
                break;
        }
    }
}
