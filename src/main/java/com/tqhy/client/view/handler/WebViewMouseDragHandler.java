package com.tqhy.client.view.handler;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/8/10
 * @since 1.0.0
 */
public class WebViewMouseDragHandler implements EventHandler<MouseEvent> {
    private Stage stage;
    private WebView webView;
    private boolean titleFlag;
    double x;
    double y;
    double screenX;
    double screenY;
    private Logger logger = LoggerFactory.getLogger(WebViewMouseDragHandler.class);

    public WebViewMouseDragHandler(Stage stage, WebView webView) {
        this.stage = stage;
        this.webView = webView;
    }

    @Override
    public void handle(MouseEvent event) {
        EventType<? extends MouseEvent> eventType = event.getEventType();

        screenX = event.getScreenX();
        screenY = event.getScreenY();
        switch (eventType.getName()) {
            case "MOUSE_PRESSED":
                double pressX = event.getX();
                double pressY = event.getY();
                titleFlag = (pressY >= 0 && pressY <= 33 && pressX >= 0 && pressX <= 400);
                if (titleFlag) {
                    x = pressX;
                    y = pressY;
                }
                logger.info("pressed...titleFlag: " + titleFlag);
                logger.info("x: " + x + " ,y: " + y);
                break;
            case "MOUSE_RELEASED":
                if (titleFlag) {
                    stage.setX(screenX - x);
                    stage.setY(screenY - y);
                }
                titleFlag = false;
                logger.info("released...");
                break;
            case "DRAG_DETECTED":
                if (titleFlag) {
                    webView.startFullDrag();
                }
                logger.info("drag detected...");
                break;
            case "MOUSE_DRAGGED":
                if (titleFlag) {
                    stage.setX(screenX - x);
                    stage.setY(screenY - y);
                }
                logger.info("dragging...");
                break;
        }
    }
}
