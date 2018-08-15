package com.tqhy.client.controller;

import com.tqhy.client.network.Network;
import com.tqhy.client.network.app.JavaAppAiPrompt;
import com.tqhy.client.network.app.JavaAppBase;
import com.tqhy.client.view.FxmlUtils;
import com.tqhy.client.view.ViewsUtils;
import com.tqhy.client.view.animation.StageMovingAnim.StageMovingAnimMode;
import com.tqhy.client.view.handler.WebViewMouseDragHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    private JavaAppBase javaApp;
    public static final int WEB_TYPE_AI_PROMPT = 0;
    public static final int WEB_TYPE_AI_WARNING = 1;
    public static final int WEB_TYPE_AI_INFO = 2;

    public WebViewController() {
        FxmlUtils.load("/dialog/web/web.fxml", this);
        Scene scene = new Scene(this);
        stage = new Stage();
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/deploy/package/windows/logo_title.png")));
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            logger.info("webview window on close request...");
            setWebViewShowing(false);
        });

        stage.setOnHiding(event -> {
            logger.info("webview window on hiding request...");
            setWebViewShowing(false);
        });


    }

    public void showWeb(String url, int webType) {
        WebEngine engine = webView.getEngine();

        switch (webType) {
            case WEB_TYPE_AI_PROMPT:
                WebViewMouseDragHandler webViewMouseDragHandler = new WebViewMouseDragHandler(stage, webView);
                webView.addEventHandler(MouseEvent.ANY, webViewMouseDragHandler);

                javaApp = new JavaAppAiPrompt(stage);
                engineBindApp(engine, javaApp);
                stage.setAlwaysOnTop(true);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setWidth(400);
                stage.setHeight(668);
                ViewsUtils.setStageAnimation(stage, StageMovingAnimMode.SLIDE_IN_FROM_TOP_RIGHT);
                logger.info("stage animation set finish..");
                break;
            case WEB_TYPE_AI_INFO:
                stage.setResizable(false);
                break;
            case WEB_TYPE_AI_WARNING:
                javaApp.setStage(stage);
                engineBindApp(engine, javaApp);
                stage.setAlwaysOnTop(true);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setWidth(300);
                stage.setHeight(150);
                break;
            default:
                break;
        }
        engine.load(url);
        setWebViewShowing(true);
        logger.info("showWeb(): " + webViewShowing.get());
        //stage.setMaximized(true);

        stage.show();
        logger.info("after stage show..");
    }

    private void engineBindApp(WebEngine engine, JavaAppBase javaApp) {
        engine.getLoadWorker()
              .stateProperty()
              .addListener((ov, oldState, newState) -> {
                  if (Worker.State.SUCCEEDED == newState) {
                      JSObject window = (JSObject) engine.executeScript("window");
                      window.setMember("tqClient", javaApp);
                      logger.info("set tqClient...");
                  }
              });
    }

    public void showTqWeb(String id, String pageName) {
        String url = Network.BASE_URL + "/html/index.html?id=" + id + "&pageName=" + pageName;
        showWeb(url, WEB_TYPE_AI_PROMPT);
    }

    public void showLocalWeb(String url) {
        String path = WebViewController.class.getResource(url).toExternalForm();
        showWeb(path, WEB_TYPE_AI_WARNING);
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

    public JavaAppBase getJavaApp() {
        return javaApp;
    }

    public void setJavaApp(JavaAppBase javaApp) {
        this.javaApp = javaApp;
    }
}
