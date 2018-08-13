package com.tqhy.client.network.app;

import javafx.stage.Stage;

/**
 * @author Yiheng
 * @create 2018/8/10
 * @since 1.0.0
 */
public class JavaAppAiWarning extends JavaAppBase {

    /**
     * 用户点击详情按钮
     */
    public void pressDetail() {

    }

    /**
     * 用户点击排除按钮
     */
    public void pressExclude() {

    }

    /**
     * 关闭当前web页面窗口
     */
    public void closeWindow() {
        logger.info("close window...");
        stage.close();
    }

    public JavaAppAiWarning(Stage stage) {
        this.stage = stage;
    }
}
