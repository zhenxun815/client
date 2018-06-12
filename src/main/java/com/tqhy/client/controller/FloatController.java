package com.tqhy.client.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FloatController {
    private double xOffset = 0;
    private double yOffset = 0;
    private MenuController menu;
    @FXML AnchorPane anchorPane;

    public FloatController() {
        menu = new MenuController();
    }

    public void press(MouseEvent event) {
        event.consume();
        MouseButton button = event.getButton();
        if (MouseButton.SECONDARY == button) {
            System.out.println(button.name() + "....");

            double x = event.getScreenX();
            double y = event.getScreenY();
            menu.showMenu(anchorPane, x, y);
        } else if (MouseButton.PRIMARY.equals(button)) {
            menu.hideMenu();
            if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
               // System.out.println("left press...");
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        }

    }

    public void drag(MouseEvent event) {
        event.consume();
        MouseButton button = event.getButton();
        if (MouseButton.SECONDARY == button) {
            //System.out.println(button.name() + "....");
        } else if (MouseButton.PRIMARY.equals(button)) {
            if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
                //System.out.println("left drag..");
                Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                if (event.getScreenY() - yOffset < 0) {
                    stage.setY(0);
                } else {
                    stage.setY(event.getScreenY() - yOffset);
                }
            }
        }
    }
}