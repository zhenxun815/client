package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * ListViewDialog ListView中每条item的Controller类
 *
 * @author Yiheng
 * @create 2018/6/11
 * @since 1.0.0
 */
public class ListViewItemController extends ListCell<String> {
    @FXML
    HBox item_container;
    @FXML
    Label lb_item;
    @FXML
    Button bt_confirm;
    @FXML
    Button bt_exclude;

    public ListViewItemController() {
        FxmlUtils.load("/dialog/list_view/list_view_item.fxml", this);
        bt_confirm.setOnAction(this::confirm);
        bt_exclude.setOnAction(this::exclude);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        //System.out.println(this + " updata item: " + item + " empty: " + empty);
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(item_container);
            setData(item);
        }
    }

    public void setData(String data) {
        lb_item.setText(data);
    }

    /**
     * 列表中条目确认按钮触发
     *
     * @param event
     */
    public void confirm(ActionEvent event) {
        System.out.println("确认..." + lb_item.getText());
        remove();
    }

    /**
     * 列表中条目确认排除触发
     *
     * @param event
     */
    public void exclude(ActionEvent event) {
        System.out.println("排除..." + lb_item.getText());
        remove();
    }

    /**
     * 移除当前条目
     */
    private void remove() {
        ListView<String> listView = getListView();
        ObservableList<String> items = listView.getItems();
        int index = getIndex();
        items.remove(index);
        listView.refresh();
    }
}
