package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class MenuController extends ContextMenu {
    @FXML
    HBox menuItemDetail;
    @FXML
    HBox menuItemConfig;
    @FXML
    HBox menuItemMin;
    @FXML
    HBox menuItemExit;
    @FXML
    HBox menuItemInfo;

    Node anchor;

    public MenuController() {
        FxmlUtils.load("/menu/menu_root.fxml", this);
        initItems();
    }

    @FXML
    public void detail(MouseEvent event) {
        System.out.println("detail click...");
    }

    @FXML
    public void config(MouseEvent event) {
        System.out.println("config click...");
    }

    @FXML
    public void exit(MouseEvent event) {
        System.out.println("exit click...");
        System.exit(0);
    }

    @FXML
    public void min(MouseEvent event) {
        System.out.println("min click...");
        Stage window = (Stage) anchor.getScene().getWindow();
        System.out.println("window is: " + window);
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = createPopMenu(window);
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/deploy/package/windows/client.png"));
            final TrayIcon trayIcon = new TrayIcon(image, "打开悬浮窗", popupMenu);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    super.mouseClicked(e);
                    if (java.awt.event.MouseEvent.BUTTON1 == e.getButton()) {
                        if (2 == e.getClickCount()) {
                            System.out.println("双击666...");
                            Platform.runLater(window::show);
                        }
                    }
                }
            });
            systemTray.add(trayIcon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Platform.runLater(window::hide);

    }

    private PopupMenu createPopMenu(Stage window) {
        PopupMenu popup = new PopupMenu();

        MenuItem itemShowDetail = new MenuItem("Detail");
        MenuItem itemShowFloat = new MenuItem("Float");
        MenuItem itemConfig = new MenuItem("Config");
        MenuItem itemExit = new MenuItem("Exit");
        itemShowDetail.addActionListener(e -> {
            System.out.println("click itemShowDetail...");
        });
        itemShowFloat.addActionListener(e -> {
            System.out.println("click itemShowFloat...");
            Platform.runLater(window::show);
        });
        itemConfig.addActionListener(e -> {
            System.out.println("click itemConfig...");
        });
        itemExit.addActionListener(e -> {
            System.out.println("click itemExit...");
            System.exit(0);
        });
        popup.add(itemShowDetail);
        popup.add(itemShowFloat);
        popup.add(itemConfig);
        popup.add(itemExit);
        return popup;
    }

    @FXML
    public void info(MouseEvent event) {
        System.out.println("info click...");
    }

    public void initItems() {
        // System.out.println("menu controller init items...");
        getItems().add(0, new CustomMenuItem(menuItemDetail));
        getItems().add(1, new CustomMenuItem(menuItemConfig));
        getItems().add(2, new CustomMenuItem(menuItemMin));
        getItems().add(3, new CustomMenuItem(menuItemExit));
        getItems().add(4, new CustomMenuItem(menuItemInfo));
    }

    public void showMenu(Node anchor, double screenX, double screenY) {
        // System.out.println("menu controller show...");
        // System.out.println("menu items size: " + getItems().size());
        // System.out.println("menu anchor is: " + anchor + " x: " + screenX + " y: " + screenY);
        this.anchor = anchor;
        show(anchor, screenX, screenY);
    }

    public void hideMenu() {
        // System.out.println("menu controller hide...");
        hide();
    }
}
