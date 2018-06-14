package com.tqhy.client;

import com.tqhy.client.controller.ListViewDialogController;
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

import java.util.concurrent.TimeUnit;

public class Main extends Application {
    TestMsg msg = new TestMsg();

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        primaryStage.setX(ViewsUtils.getMaxX(50));
        primaryStage.setY(ViewsUtils.getMaxY(50));
        primaryStage.show();
        msg.desc = "嘿嘿";
        //doNetWork();
    }

    /**
     * 通过轮询请求后台,获取当前查看影像信息
     */
    private void doNetWork() {
        Network.getAiResultApi()
                .getTest()
                //.getAiResult("doscan")
                .repeatWhen(objectObservable ->
                        objectObservable.flatMap(o ->
                                Observable.just(1).delay(3000, TimeUnit.MILLISECONDS)))
                .filter(testMsg -> !msg.equals(testMsg))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(testMsg ->
                        Platform.runLater(() -> {
                            System.out.println(testMsg);
                            ListViewDialogController listViewDialogController = new ListViewDialogController(testMsg);
                            listViewDialogController.showAtRightBottom();
                        })
                );
    }


    public static void main(String[] args) {
        launch(args);
    }


}
