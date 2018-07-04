package com.tqhy.client.controller;

import com.tqhy.client.utils.ViewsUtils;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatController {
    private double xOffset = 0;
    private double yOffset = 0;
    private MenuController menu;
    private Logger logger = LoggerFactory.getLogger(FloatController.class);
    @FXML
    AnchorPane anchorPane;

    public FloatController() {
        menu = new MenuController();
    }

    public void press(MouseEvent event) {
        //anchorPane.setStyle("-fx-background-color: green;");
        event.consume();
        MouseButton button = event.getButton();
        if (MouseButton.SECONDARY == button) {
            logger.info(button.name() + "....");

            double x = event.getScreenX();
            double y = event.getScreenY();
            menu.showMenu(anchorPane, x, y);
        } else if (MouseButton.PRIMARY.equals(button)) {
            menu.hideMenu();
            if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
                // logger.info("left press...");
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        }

    }

    public void drag(MouseEvent event) {
        event.consume();
        //anchorPane.setStyle("-fx-background-color: lightseagreen;");
        MouseButton button = event.getButton();
        if (MouseButton.SECONDARY == button) {
            //logger.info(button.name() + "....");
        } else if (MouseButton.PRIMARY.equals(button)) {
            if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
                //logger.info("left drag..");
                Stage stage = (Stage) anchorPane.getScene().getWindow();
                double width = anchorPane.getWidth();
                double height = anchorPane.getHeight();
                double x = event.getScreenX() - xOffset;
                double y = event.getScreenY() - yOffset;
                double maxX = ViewsUtils.getMaxX(width);
                double maxY = ViewsUtils.getMaxY(height);
                stage.setX(x < maxX * 2 / 3 ? maxX * 2 / 3 : (x > maxX ? maxX : x));
                stage.setY(y < maxY * 2 / 3 ? maxY * 2 / 3 : (y > maxY ? maxY : y));
            }
        }
    }
}