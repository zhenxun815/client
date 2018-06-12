package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class MenuController extends ContextMenu {
    @FXML
    HBox menuItemDetail;
    @FXML
    HBox menuItemConfig;
    @FXML
    HBox menuItemMin;
    @FXML
    HBox menuItemExit;
    @FXML
    HBox menuItemInfo;

    public MenuController() {
        FxmlUtils.load("/menu/menu_root.fxml", this);
        initItems();
    }

    @FXML
    public void detail(MouseEvent event) {
        System.out.println("detail click...");
    }

    @FXML
    public void config(MouseEvent event) {
        System.out.println("config click...");
    }

    @FXML
    public void exit(MouseEvent event) {
        System.out.println("exit click...");
        System.exit(0);
    }

    @FXML
    public void min(MouseEvent event) {
        System.out.println("min click...");
    }

    @FXML
    public void info(MouseEvent event) {
        System.out.println("info click...");
        DialogController dialogController = new DialogController();
        dialogController.show();
    }

    public void initItems() {
        System.out.println("menu controller init items...");
        getItems().add(0, new CustomMenuItem(menuItemDetail));
        getItems().add(1, new CustomMenuItem(menuItemConfig));
        getItems().add(2, new CustomMenuItem(menuItemMin));
        getItems().add(3, new CustomMenuItem(menuItemExit));
        getItems().add(4, new CustomMenuItem(menuItemInfo));
    }

    public void showMenu(Node anchor, double screenX, double screenY) {
        System.out.println("menu controller show...");
        System.out.println("menu items size: " + getItems().size());
        System.out.println("menu anchor is: " + anchor + " x: " + screenX + " y: " + screenY);
        show(anchor, screenX, screenY);
    }

    public void hideMenu() {
        System.out.println("menu controller hide...");
        hide();
    }
}
