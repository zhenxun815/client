package com.tqhy.client.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Yiheng
 * @create 2018/6/11
 * @since 1.0.0
 */
public class ListViewItemController {

    HBox item_container;

    Label lb_item;

    Button bt_confirm;

    Button bt_exclude;

    public ListViewItemController(HBox hBox) {
        item_container = hBox;
        ObservableList<Node> children = hBox.getChildren();
        for (Node node : children) {
            switch (node.getId()) {
                case "bt_confirm":
                    bt_confirm = (Button) node;
                    break;
                case "bt_exclude":
                    bt_exclude = (Button) node;
                    break;
                case "lb_item":
                    lb_item = (Label) node;
                    break;
            }
        }

        bt_confirm.setOnAction(this::confirm);
        bt_exclude.setOnAction(this::exclude);
    }

    public void setData(String data) {
        lb_item.setText(data);
    }

    public void confirm(ActionEvent event) {
        System.out.println("确认..." + lb_item.getText());
    }

    public void exclude(ActionEvent event) {
        System.out.println("排除..." + lb_item.getText());
    }

    public HBox getItemView() {
        return item_container;
    }

}
