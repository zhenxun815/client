package com.tqhy.client.controller;

import com.tqhy.client.network.JavaApp;
import com.tqhy.client.network.Network;
import com.tqhy.client.utils.FxmlUtils;
import com.tqhy.client.utils.ViewsUtils;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
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
        });
    }

    public void showWeb(String url) {
        WebEngine engine = webView.getEngine();
        engine.load(url);
        setWebViewShowing(true);
        logger.info("showWeb(): " + webViewShowing.get());
        //stage.setMaximized(true);
        showAnimation();
        stage.show();
    }

    public void showTqWeb(String id, String pageName) {
        String url = Network.BASE_URL + "index.html?id=" + id + "&pageName=" + pageName;
        showWeb(url);
    }

    public void showLocalWeb(String url) {
        WebEngine engine = webView.getEngine();
        String path = WebViewController.class.getResource(url).toExternalForm();
        engine.load(path);

        showAnimation();
        stage.show();
        JSObject window = (JSObject) engine.executeScript("window");
        JavaApp javaApp = new JavaApp(stage);
        window.setMember("app", javaApp);
    }

    private void showAnimation() {
        stage.setOnShowing(event -> {
            logger.info("into onShown...");
            Pane pane = new Pane();
            TranslateTransition transTrans = new TranslateTransition(Duration.millis(500), pane);
            double screenWidth = ViewsUtils.getScreenWidth();
            double screenHeight = ViewsUtils.getScreenHeight();
            double originWidth = screenWidth / 3;
            double originHeight = 4 * screenHeight / 5;
            stage.setWidth(originWidth);
            stage.setHeight(originHeight);
            transTrans.setFromX(screenWidth - originWidth);
            transTrans.setFromY(-originWidth);
            transTrans.setToX(screenWidth - originWidth);
            transTrans.setToY(0);
            pane.translateXProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        stage.setX(newValue.doubleValue());
                        this.setLayoutX(0D);
                    });
            pane.translateYProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        stage.setY(newValue.doubleValue());
                        this.setLayoutY(0D);
                    });

            ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(500), pane);
            scaleTrans.setFromX(0.2D);
            scaleTrans.setToX(1.0D);

            pane.scaleXProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        logger.info("newValue is: " + newValue);
                        logger.info("scene width is: " + this.getWidth());
                        logger.info("stage width is: " + stage.getWidth());
                        //this.setWidth(newValue.doubleValue() * originWidth);
                        stage.setWidth(newValue.doubleValue() * originWidth);
                    });


            ParallelTransition paralTrans = new ParallelTransition(pane, transTrans);
            paralTrans.play();
            //stage.setResizable(false);

            logger.info("play finish...");
        });
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
