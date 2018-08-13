package com.tqhy.client.network.app;

import javafx.stage.Stage;

/**
 * @author Yiheng
 * @create 2018/6/20
 * @since 1.0.0
 */
public class JavaAppAiPrompt extends JavaAppBase {

    /**
     * 关闭当前web页面窗口
     */
    public void closeWindow() {
        logger.info("close window...");
        stage.close();
    }

    public JavaAppAiPrompt(Stage stage) {
        this.stage = stage;
    }
}