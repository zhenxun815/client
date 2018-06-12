package com.tqhy.client.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class TqhyDialog {

    private Dialog<String> dialog;
    private Stage owner;

    public TqhyDialog() {
        initViews();
    }

    private void initViews() {

        try {
            dialog = new Dialog<>();
            owner = new Stage();
            owner.getIcons().addAll(new Image(TqhyDialog.class.getResourceAsStream("/deploy/package/windows/client.png")));
            owner.setScene(new Scene(new Group()));
            System.out.println("before load dialog ...");
            DialogPane dialogPane = FXMLLoader.load(TqhyDialog.class.getResource("/dialog/dialog.fxml"));
            System.out.println("after load dialog...");
            dialog.initOwner(owner);
            dialog.setDialogPane(dialogPane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void show() {
        Screen primary = Screen.getPrimary();
        Rectangle2D visualBounds = primary.getVisualBounds();
        double maxX = visualBounds.getMaxX();
        double maxY = visualBounds.getMaxY();
        double height = visualBounds.getHeight();
        double width = visualBounds.getWidth();
        double minX = visualBounds.getMinX();
        double minY = visualBounds.getMinY();
        System.out.println("max x: " + maxX + " max y: " + maxY);
        System.out.println("min x: " + minX + " min y: " + minY);
        System.out.println("width: " + width + " height: " + height);
    /*  dialog.setWidth(100);
        dialog.setHeight(70);
        double dWidth = dialog.getWidth();
        double dHeight = dialog.getHeight();
        System.out.println("dWidth: " + dWidth + " dHeight: " + dHeight);*/
        dialog.setX(width - dialog.getWidth());
        dialog.setY(height - dialog.getHeight());
        System.out.println("x: " + dialog.getX() + " y: " + dialog.getY());
        dialog.showAndWait();
    }
}
