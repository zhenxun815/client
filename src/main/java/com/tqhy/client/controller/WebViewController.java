package com.tqhy.client.controller;

import com.tqhy.client.network.JavaApp;
import com.tqhy.client.network.Network;
import com.tqhy.client.view.FxmlUtils;
import com.tqhy.client.view.ViewsUtils;
import com.tqhy.client.view.animation.StageMovingAnim.StageMovingAnimMode;
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
import javafx.stage.StageStyle;
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
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/logo_title.png")));
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            logger.info("webview window on close request...");
            setWebViewShowing(false);
            stage.close();
        });
    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        logger.info("webview width: " + webView.getWidth() + " ,height: " + webView.getHeight());
        JavaApp javaApp = new JavaApp(stage);
        engine.getLoadWorker()
              .stateProperty()
              .addListener((ov, oldState, newState) -> {
                  if (Worker.State.SUCCEEDED == newState) {
                      JSObject window = (JSObject) engine.executeScript("window");
                      window.setMember("tqClient", javaApp);
                      logger.info("set tqClient...");
                  }
              });
        engine.load(url);
        setWebViewShowing(true);
        logger.info("showWeb(): " + webViewShowing.get());
        //stage.setMaximized(true);
        //ViewsUtils.setStageScaleAccordingScreen(stage, 1 / 3.0D, 4 / 5.0D);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(400);
        stage.setHeight(668);

        ViewsUtils.setStageAnimation(stage, StageMovingAnimMode.SLIDE_IN_FROM_TOP_RIGHT);
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
