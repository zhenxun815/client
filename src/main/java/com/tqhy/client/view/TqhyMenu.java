package com.tqhy.client.view;

import com.tqhy.client.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class TqhyMenu extends ContextMenu {
    public TqhyMenu() {
        initItems();
    }

    public  void initItems() {
        try {
            HBox itemDetail = FXMLLoader.load(TqhyMenu.class.getResource("/menu/menu_item_detail.fxml"));
            HBox itemConfig = FXMLLoader.load(TqhyMenu.class.getResource("/menu/menu_item_config.fxml"));
            HBox itemMin = FXMLLoader.load(TqhyMenu.class.getResource("/menu/menu_item_min.fxml"));
            HBox itemExit = FXMLLoader.load(TqhyMenu.class.getResource("/menu/menu_item_exit.fxml"));
            HBox itemInfo = FXMLLoader.load(TqhyMenu.class.getResource("/menu/menu_item_info.fxml"));

            getItems().add(0, new CustomMenuItem(itemDetail));
            getItems().add(1, new CustomMenuItem(itemConfig));
            getItems().add(2, new CustomMenuItem(itemMin));
            getItems().add(3, new CustomMenuItem(itemExit));
            getItems().add(4, new CustomMenuItem(itemInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
