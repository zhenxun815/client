package com.tqhy.client.network.app;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/8/10
 * @since 1.0.0
 */
public class JavaAppBase {
    public String id = "id";
    public Stage stage;
    public Logger logger = LoggerFactory.getLogger(JavaAppBase.class);

    public JavaAppBase() {
    }

    /**
     * 在java控制台输出js内容
     *
     * @param log
     */
    public void showLog(String log) {
        logger.info("http log: " + log);
    }

    /**
     * 关闭当前web页面窗口
     */
    public void closeWindow() {
        logger.info("close window...");
        Platform.runLater(() -> {
            stage.close();
        });
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
