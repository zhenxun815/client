package com.tqhy.client.controller;

import com.tqhy.client.network.JavaApp;
import com.tqhy.client.network.Network;
import com.tqhy.client.utils.FxmlUtils;
import com.tqhy.client.utils.ViewsUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public class WebViewDialogController extends AnchorPane {

    @FXML
    WebView webView;

    private Stage stage;
    private BooleanProperty webViewShowing = new SimpleBooleanProperty();
    private Logger logger = LoggerFactory.getLogger(WebViewDialogController.class);

    //"/dialog/web_dialog/web.fxml"
    public WebViewDialogController() {
        FxmlUtils.load("/dialog/web/web.fxml", this);
        Scene scene = new Scene(this);
        stage = new Stage();
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            logger.info("webview window on close request...");
            setWebViewShowing(false);
        });
    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        engine.load(url);
        setWebViewShowing(true);
        logger.info("showWeb(): "+webViewShowing.get());
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
        String path = WebViewDialogController.class.getResource(url).toExternalForm();
        engine.load(path);
        stage.show();
        JSObject window = (JSObject) engine.executeScript("window");
        JavaApp javaApp = new JavaApp(stage);
        window.setMember("app", javaApp);
    }

    public boolean isWebViewShowing() {
        return webViewShowing.get();
    }

    public BooleanProperty webViewShowingProperty() {
        return webViewShowing;
    }

    public void setWebViewShowing(boolean webViewShowing) {
        this.webViewShowing.set(webViewShowing);
    }
}
