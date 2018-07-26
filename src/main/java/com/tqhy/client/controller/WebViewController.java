package com.tqhy.client.controller;

import com.tqhy.client.network.JavaApp;
import com.tqhy.client.network.Network;
import com.tqhy.client.utils.FxmlUtils;
import com.tqhy.client.utils.ViewsUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker;
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
public class WebViewController extends AnchorPane {

    @FXML
    WebView webView;

    private Stage stage;
    private BooleanProperty webViewShowing = new SimpleBooleanProperty();
    private Logger logger = LoggerFactory.getLogger(WebViewController.class);

    //"/dialog/web_dialog/web.fxml"
    public WebViewController() {
        FxmlUtils.load("/dialog/web/web.fxml", this);
        Scene scene = new Scene(this);
        stage = new Stage();
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            logger.info("webview window on close request...");
            setWebViewShowing(false);
            stage.close();
        });
    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        JavaApp javaApp = new JavaApp(stage);
        engine.getLoadWorker()
              .stateProperty()
              .addListener((ov, oldState, newState) -> {
                  if (Worker.State.SUCCEEDED == newState) {
                      JSObject window = (JSObject) engine.executeScript("window");
                      window.setMember("tq_client", javaApp);
                      logger.info("set tq_client...");
                  }
              });
        engine.load(url);
        setWebViewShowing(true);
        logger.info("showWeb(): " + webViewShowing.get());
        //stage.setMaximized(true);
        ViewsUtils.stageShowingAnimation(stage);
        stage.show();
    }

    public void showTqWeb(String id, String pageName) {
        String url = Network.BASE_URL + "index.html?id=" + id + "&pageName=" + pageName;
        showWeb(url);
    }

    public void showLocalWeb(String url) {
        String path = WebViewController.class.getResource(url).toExternalForm();
        showWeb(path);
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
