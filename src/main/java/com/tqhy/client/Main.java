package com.tqhy.client;

import com.tqhy.client.controller.PaneDialogController;
import com.tqhy.client.model.bean.AiResult;
import com.tqhy.client.model.bean.TestMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.utils.ViewsUtils;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    TestMsg msg = new TestMsg();

    @Override
    public void start(Stage primaryStage) throws Exception {

        initPrimaryStage(primaryStage);
        msg.desc = "嘿嘿";
        initSystemTray(primaryStage);
        doNetWork(primaryStage);
    }

    /**
     * 初始化主窗口
     *
     * @param primaryStage
     * @throws IOException
     */
    private void initPrimaryStage(Stage primaryStage) throws IOException {
        Platform.setImplicitExit(false);
        Parent root = FXMLLoader.load(getClass().getResource("/float/float.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        Rectangle rect = new Rectangle(50, 50);
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        Scene scene = new Scene(root, 50, 50);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setX(ViewsUtils.getMaxX(50 * 2));
        primaryStage.setY(ViewsUtils.getMaxY(50 * 2));
        primaryStage.show();
    }

    /**
     * 通过轮询请求后台,获取当前查看影像信息
     */
    private void doNetWork(Stage primaryStage) {
        Network.getAiResultApi()
                .getTest()
                //.getAiResult("doscan")
                .repeatWhen(objectObservable ->
                        objectObservable.flatMap(o ->
                                Observable.just(1).delay(5000, TimeUnit.MILLISECONDS)))
                .filter(testMsg -> !msg.equals(testMsg))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(testMsg ->
                        Platform.runLater(() -> {
                            System.out.println(testMsg);
                            primaryStage.getScene().getRoot().setStyle("-fx-background-color: red;");
                            PaneDialogController paneDialogController = new PaneDialogController(new AiResult());
                            paneDialogController.show(primaryStage);

                        })
                );
    }

    /**
     * 创建系统托盘图标
     *
     * @param stage
     */
    private void initSystemTray(Stage stage) {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = createPopMenu(stage);
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
                            Platform.runLater(stage::show);
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
    }

    private PopupMenu createPopMenu(Stage window) {
        PopupMenu popup = new PopupMenu();
        MenuItem itemDetail = new MenuItem("详情");
        MenuItem itemFloat = new MenuItem("悬窗");
        MenuItem itemConfig = new MenuItem("设置");
        MenuItem itemExit = new MenuItem("退出");
        itemDetail.addActionListener(e -> {
            System.out.println("click itemShowDetail...");
        });
        itemFloat.addActionListener(e -> {
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
        popup.add(itemDetail);
        popup.add(itemFloat);
        popup.add(itemConfig);
        popup.add(itemExit);
        return popup;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
