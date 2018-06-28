package com.tqhy.client.controller;

import com.tqhy.client.utils.ViewsUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class BaseDialogController extends DialogPane {

    protected Dialog<ButtonType> dialog;
    protected Stage owner;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化Dialog,子类构造方法中加载完fxml文件后调用.
     */
    protected void initDialog() {
        owner = new Stage();
        owner.setAlwaysOnTop(true);
        owner.setScene(new Scene(new Group()));

        dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane(this);
        dialog.initOwner(owner);
    }

    /**
     * 右下角显示弹窗
     */
    public Optional<ButtonType> showAtRightBottom() {
        dialog.setX(ViewsUtils.getMaxX(this.getWidth()));
        dialog.setY(ViewsUtils.getMaxY(this.getHeight()));
        // logger.info("x: " + dialog.getX() + " y: " + dialog.getY());
        // dialog.showAndWait();
        Optional<ButtonType> cmd = dialog.showAndWait();
        return cmd;
    }

    /**
     * 屏幕中心显示弹窗
     */
    public Optional<ButtonType> showAtCenter() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        dialog.setX(visualBounds.getMaxX() / 2 - this.getWidth() / 2);
        dialog.setY(visualBounds.getMaxY() / 2 - this.getHeight() / 2);
        // logger.info("x: " + dialog.getX() + " y: " + dialog.getY());
        // dialog.showAndWait();
        Optional<ButtonType> cmd = dialog.showAndWait();
        return cmd;
    }
}
