package com.tqhy.client.controller;

import com.tqhy.client.network.JavaApp;
import com.tqhy.client.network.Network;
import com.tqhy.client.utils.FxmlUtils;
import com.tqhy.client.utils.ViewsUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public class WebViewDialogController extends AnchorPane {

    @FXML
    WebView webView;

    private Stage stage;

    //"/dialog/web_dialog/web_dialog.fxml"
    public WebViewDialogController() {
        FxmlUtils.load("/dialog/web_dialog/web_dialog.fxml", this);
        double screenWidth = ViewsUtils.getScreenWidth();
        double screenHeight = ViewsUtils.getScreenHeight();
        Scene scene = new Scene(this);
        stage = new Stage();
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        stage.setScene(scene);


    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        engine.load(url);
      /*engine.setOnResized(
                new EventHandler<WebEvent<Rectangle2D>>() {
                    public void handle(WebEvent<Rectangle2D> ev) {
                        Rectangle2D r = ev.getData();
                        stage.setWidth(r.getWidth());
                        stage.setHeight(r.getHeight());
                    }
                });*/
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.show();
    }

    public void showTqWeb(String id, String pageName) {
        String url = Network.BASE_URL + "index?id=" + id + "&pageName=" + pageName;
        showWeb(url);
    }

    public void showLocalWeb(String url) {
        WebEngine engine = webView.getEngine();
      /*engine.setOnResized(
                new EventHandler<WebEvent<Rectangle2D>>() {
                    public void handle(WebEvent<Rectangle2D> ev) {
                        Rectangle2D r = ev.getData();
                        stage.setWidth(r.getWidth());
                        stage.setHeight(r.getHeight());
                    }
                });*/
        String path = WebViewDialogController.class.getResource(url).toExternalForm();
        engine.load(path);
        stage.show();
        JSObject window = (JSObject) engine.executeScript("window");
        JavaApp javaApp = new JavaApp(stage);
        window.setMember("app", javaApp);
    }

}
