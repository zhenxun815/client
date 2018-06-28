package com.tqhy.client;

import com.google.gson.Gson;
import com.tqhy.client.controller.AiWarningDialogController;
import com.tqhy.client.controller.AuthWarningDialogController;
import com.tqhy.client.jna.JnaTest;
import com.tqhy.client.model.bean.AiResult;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.response.ErrorResponseBody;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private String key = "";
    private Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        initPrimaryStage(primaryStage);

        initSystemTray(primaryStage);
        doRxJava(primaryStage);
        //requestAiHelper(primaryStage);
    }

    /**
     * 循环调用dll文件获取key并过滤,如果是新的key则向后台请求弹出警告弹框
     *
     * @param primaryStage
     */
    private void doRxJava(Stage primaryStage) {
        Observable.interval(3000, TimeUnit.MILLISECONDS)
                .map(aLong -> {
                            int i = JnaTest.caller.jyTestFunc(2, 3);
                            logger.info(".dll caller get: "+i);
                            //todo Network.currentId=key;
                            return "0026086fd6654dbfb3d2a3e78cf67140";
                            //return "2";
                        }
                )
                .filter(key -> {
                    boolean b = key.equals(this.key);
                    //logger.info("key: " + key + "this.key: " + this.key + "b: " + b);
                    this.key = key;
                    return !b;
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(key -> {
                    switch (key) {
                        //未授权
                        case "1":
                            getAuthWarning(primaryStage);
                            break;
                        //非RIS界面
                        case "2":
                           hidefloat(primaryStage);
                            break;
                        default:
                            getAiHelperWarning(primaryStage, key);
                            break;
                    }
                    //logger.info("subscribe: " + key);
                });
    }

    /**
     * 隐藏悬浮窗
     * @param primaryStage
     */
    private void hidefloat(Stage primaryStage) {
        Platform.runLater(()-> primaryStage.hide());
    }

    private void getAuthWarning(Stage primaryStage) {
        Platform.runLater(() -> {
            AuthWarningDialogController authWarningDialogController = new AuthWarningDialogController();
            String show = authWarningDialogController.show(primaryStage);
            key = show;
        });

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
     * 请求后台,获取警告弹框内容
     */
    private void getAiHelperWarning(Stage primaryStage, String key) {
        Network.getAiHelperApi()
                .requestAiHelper(key)
                /* .repeatWhen(objectObservable ->
                         objectObservable.flatMap(o ->
                                 Observable.just(1).delay(5000, TimeUnit.MILLISECONDS)))
                 .filter(testMsg -> !key.equals(testMsg))*/
                .onErrorReturn((error) -> new ErrorResponseBody(error))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(data -> {
                    if (data instanceof ErrorResponseBody) {
                        logger.info("subscribe error " + data.string());
                    } else {
                        String json = data.string();
                        Platform.runLater(() -> {
                            logger.info("subscribe str: " + json);
                            AiResult aiResult = new Gson().fromJson(json, AiResult.class);
                            primaryStage.getScene().getRoot().setStyle("-fx-background-color: red;");
                            logger.info("subscribe aiResult : " + aiResult);
                            AiWarningDialogController aiWarningDialogController = new AiWarningDialogController(aiResult);
                            aiWarningDialogController.show(primaryStage);
                        });
                    }
                });
    }

    /**
     * 创建系统托盘图标
     *
     * @param stage
     */
    private void initSystemTray(Stage stage) {
        SystemTray systemTray = SystemTray.getSystemTray();
        //PopupMenu popupMenu = createPopMenu(stage);
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/deploy/package/windows/client.png"));
            //final TrayIcon trayIcon = new TrayIcon(image, "打开悬浮窗",popupMenu);
            final TrayIcon trayIcon = new TrayIcon(image, "双击打开悬浮窗");
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    super.mouseClicked(e);
                    if (java.awt.event.MouseEvent.BUTTON1 == e.getButton()) {
                        if (2 == e.getClickCount()) {
                            logger.info("双击666...");
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

    /**
     * 创建系统托盘图标右键菜单
     *
     * @param window
     * @return
     */
    private PopupMenu createPopMenu(Stage window) {
        PopupMenu popup = new PopupMenu();
        MenuItem itemDetail = new MenuItem("详情");
        MenuItem itemFloat = new MenuItem("悬窗");
        MenuItem itemConfig = new MenuItem("设置");
        MenuItem itemExit = new MenuItem("退出");
        itemDetail.addActionListener(e -> {
            logger.info("click itemShowDetail...");
        });
        itemFloat.addActionListener(e -> {
            logger.info("click itemShowFloat...");
            Platform.runLater(window::show);
        });
        itemConfig.addActionListener(e -> {
            logger.info("click itemConfig...");
        });
        itemExit.addActionListener(e -> {
            logger.info("click itemExit...");
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
