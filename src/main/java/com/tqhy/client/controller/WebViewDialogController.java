package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public class WebViewDialogController extends Pane {

    @FXML
    WebView webView;

    private Stage stage;

    //"/dialog/web_dialog/web_dialog.fxml"
    public WebViewDialogController() {
        FxmlUtils.load("/dialog/web_dialog/web_dialog.fxml", this);
        Scene scene = new Scene(this, 1000, 700);
        stage = new Stage();
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        stage.setScene(scene);
    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        engine.load(url);
        stage.show();
    }
}
