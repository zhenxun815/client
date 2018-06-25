package com.tqhy.client.controller;

import com.tqhy.client.network.Network;
import com.tqhy.client.utils.FxmlUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class MenuController extends ContextMenu {
    @FXML
    HBox menuItemHistory;
    @FXML
    HBox menuItemUpload;
    @FXML
    HBox menuItemMin;
    @FXML
    HBox menuItemExit;
    @FXML
    HBox menuItemInfo;

    Node anchor;
    private Stage stage;

    public MenuController() {
        FxmlUtils.load("/menu/menu_root.fxml", this);
        initItems();
    }

    @FXML
    public void history(MouseEvent event) {
        System.out.println("detail click...");
        WebViewDialogController web = new WebViewDialogController();
        //web.showWeb(Network.BASE_URL+"index?id="+Network.currentId+"&pageName=history");
        web.showTqWeb(Network.currentId, Network.HISTORY_PAGE);
    }

    @FXML
    public void upload(MouseEvent event) {
        System.out.println("upload click...");
        WebViewDialogController web = new WebViewDialogController();
        //web.showWeb(Network.BASE_URL+"index?id="+Network.currentId+"&pageName=report");
        web.showTqWeb(Network.currentId, Network.REPORT_PAGE);
    }

    @FXML
    public void exit(MouseEvent event) {
        System.out.println("exit click...");
        stage.close();
        System.exit(0);
    }

    @FXML
    public void min(MouseEvent event) {
        Platform.runLater(stage::hide);
    }

    @FXML
    public void info(MouseEvent event) {
        System.out.println("info click...");
        WebViewDialogController web = new WebViewDialogController();
        web.showLocalWeb("/html/test.html");
        //web.showWeb("http://192.168.1.212:8080/ai/helper/test/" + "testId");
    }

    public void initItems() {
        // System.out.println("menu controller init items...");
        getItems().add(0, new CustomMenuItem(menuItemHistory));
        getItems().add(1, new CustomMenuItem(menuItemUpload));
        getItems().add(2, new CustomMenuItem(menuItemMin));
        getItems().add(3, new CustomMenuItem(menuItemExit));
        getItems().add(4, new CustomMenuItem(menuItemInfo));
    }

    public void showMenu(Node anchor, double screenX, double screenY) {
        // System.out.println("menu controller showAtRightBottom...");
        // System.out.println("menu items size: " + getItems().size());
        // System.out.println("menu anchor is: " + anchor + " x: " + screenX + " y: " + screenY);
        this.anchor = anchor;
        this.stage = (Stage) anchor.getScene().getWindow();
        show(anchor, screenX, screenY);
    }

    public void hideMenu() {
        System.out.println("menu controller hide...");
        hide();
    }
}
