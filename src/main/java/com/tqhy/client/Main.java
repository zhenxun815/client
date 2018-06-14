package com.tqhy.client;

import com.tqhy.client.controller.ListViewDialogController;
import com.tqhy.client.network.Network;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.TimeUnit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        Parent root = FXMLLoader.load(getClass().getResource("/float/float.fxml"));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/deploy/package/windows/client.png")));
        Scene scene = new Scene(root, 50, 50);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        doNetWork();
    }

    /**
     * 通过轮询请求后台,获取当前查看影像信息
     */
    private void doNetWork() {
        Network.getAiResultApi()
                .getTest()
                .repeatWhen(objectObservable ->
                        objectObservable.flatMap(o ->
                                Observable.just(1).delay(3000, TimeUnit.MILLISECONDS)))
                .observeOn(Schedulers.trampoline())
                .subscribe(testMsg ->
                        Platform.runLater(() -> {
                            System.out.println(testMsg);
                            ListViewDialogController listViewDialogController = new ListViewDialogController(testMsg);
                            listViewDialogController.show();
                        })
                );
    }


    public static void main(String[] args) {
        launch(args);
    }
}
