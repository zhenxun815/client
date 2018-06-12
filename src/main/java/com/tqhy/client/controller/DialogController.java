package com.tqhy.client.controller;

import com.tqhy.client.view.TqhyListViewItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class DialogController {

    private List<String> datas;
    private ObservableList observableList;
    @FXML
    ListView dialog_lv;

    @FXML
    public void initialize() {
        System.out.println("construct dialog controller");
        observableList = FXCollections.observableArrayList();
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("item content " + i);
        }
        observableList.addAll(datas);
        dialog_lv.setItems(observableList);
        dialog_lv.setCellFactory(lv -> new TqhyListViewItem());
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
