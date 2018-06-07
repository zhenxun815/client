package com.tqhy.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Controller {
    @FXML
    private Button bt_test;

    public void click(MouseEvent event) {

        System.out.println(event.getButton().name() + "....");
    }
}
