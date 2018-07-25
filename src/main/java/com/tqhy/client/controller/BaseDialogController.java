package com.tqhy.client.controller;

import com.tqhy.client.utils.ViewsUtils;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    protected BooleanProperty dialogShouldShowingFlag = new SimpleBooleanProperty();

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
        setDialogShouldShowingFlag(true);
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
        setDialogShouldShowingFlag(true);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double x = visualBounds.getMaxX() / 2 - this.getWidth() / 2;
        double y = visualBounds.getMaxY() / 2 - this.getHeight() / 2;
        dialog.setX(x);
        dialog.setY(y);
        logger.info(" into showAtCenter, x: " + dialog.getX() + " y: " + dialog.getY());
        Optional<ButtonType> cmd = dialog.showAndWait();
        return cmd;
    }


    /**
     * 关闭当前ai提示弹窗
     */
    public void closeDialog() {
        logger.info("closeDialog() invoked...");

        if (dialog.isShowing()) {
            logger.info("dialog.close() invoked...");
            Platform.runLater(() -> {
                dialog.setResult(ButtonType.CLOSE);
                dialog.close();
            });
        }
    }

    public boolean getDialogShouldShowingFlag() {
        return dialogShouldShowingFlag.get();
    }

    public BooleanProperty dialogShouldShowingFlagProperty() {
        return dialogShouldShowingFlag;
    }

    public void setDialogShouldShowingFlag(boolean dialogShouldShowingFlag) {
        this.dialogShouldShowingFlag.set(dialogShouldShowingFlag);
    }
}
