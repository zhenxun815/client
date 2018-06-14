package com.tqhy.client.controller;

import com.tqhy.client.model.bean.TestMsg;
import com.tqhy.client.utils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class ListViewDialogController extends DialogPane {

    @FXML
    private ListView dialog_lv;
    private List<String> datas;
    private ObservableList observableList;
    private Dialog<String> dialog;
    private Stage owner;
    private TestMsg msg;

    public ListViewDialogController(TestMsg msg) {
        this.msg = msg;
        FxmlUtils.load("/dialog/lvdialog/lv_dialog.fxml", this);
        initDialog();
        initListViewItems();
        //System.out.println("construct dialog controller");
    }

    private void initDialog() {
        owner = new Stage();
        owner.setAlwaysOnTop(true);
        owner.setScene(new Scene(new Group()));

        dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane(this);
        dialog.initOwner(owner);
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

    /**
     * 显示弹窗.默认在桌面右下角弹出.
     */
    public void show() {
        Screen primary = Screen.getPrimary();
        Rectangle2D visualBounds = primary.getVisualBounds();
        double maxX = visualBounds.getMaxX();
        double maxY = visualBounds.getMaxY();
        double height = visualBounds.getHeight();
        double width = visualBounds.getWidth();
        double minX = visualBounds.getMinX();
        double minY = visualBounds.getMinY();
        // System.out.println("max x: " + maxX + " max y: " + maxY);
        // System.out.println("min x: " + minX + " min y: " + minY);
        // System.out.println("width: " + width + " height: " + height);
        dialog.setX(maxX - this.getWidth());

        dialog.setY(maxY - this.getHeight());
        // System.out.println("x: " + dialog.getX() + " y: " + dialog.getY());
        dialog.showAndWait();
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
