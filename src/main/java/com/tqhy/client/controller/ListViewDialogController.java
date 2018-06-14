package com.tqhy.client.controller;

import com.tqhy.client.model.bean.TestMsg;
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
    private TestMsg msg;

    public ListViewDialogController(TestMsg msg) {
        this.msg = msg;
        FxmlUtils.load("/dialog/lv_dialog/lv_dialog.fxml", this);
        initDialog();
        initListViewItems();
        //System.out.println("construct dialog controller");
    }

    /**
     * 填充ListView中每条item显示内容
     */
    private void initListViewItems() {
        observableList = FXCollections.observableArrayList();
        datas = msg.data.source;
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
