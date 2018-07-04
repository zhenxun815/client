package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class ListViewDialogController extends BaseDialogController {

    @FXML
    private ListView dialog_lv;
    private List<String> datas;
    private ObservableList observableList;
    /**
     * 列表要展示的数据
     */
    private List<String> msg;

    public ListViewDialogController(List<String> msg) {
        this.msg = msg;
        FxmlUtils.load("/dialog/list_view/list_view.fxml", this);
        initDialog();
        initListViewItems();
        //logger.info("construct dialog controller");
    }

    /**
     * 填充ListView中每条item显示内容
     */
    private void initListViewItems() {
        observableList = FXCollections.observableArrayList();
        datas = msg;
        observableList.addAll(datas);
        dialog_lv.setItems(observableList);
        dialog_lv.setCellFactory(lv -> new ListViewItemController());
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
