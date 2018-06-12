package com.tqhy.client.view;

import com.tqhy.client.controller.ListViewItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/11
 * @since 1.0.0
 */
public class TqhyListViewItem extends ListCell<String> {

    private ListViewItemController itemController;

    public TqhyListViewItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(ListViewItemController.class.getResource("/dialog/lv_item.fxml"));
        try {
            //fxmlLoader.setRoot(this);
            HBox item_container = fxmlLoader.load();
            itemController = new ListViewItemController(item_container);
            fxmlLoader.setController(itemController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("updata item: " + item + " empty: " + empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            itemController.setData(item);
            setGraphic(itemController.getItemView());
        }
    }
}
