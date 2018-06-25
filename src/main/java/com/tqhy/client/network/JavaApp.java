package com.tqhy.client.network;

import javafx.stage.Stage;

/**
 * @author Yiheng
 * @create 2018/6/20
 * @since 1.0.0
 */
public class JavaApp {
    private final Stage stage;
    public String id = "id";

    public JavaApp(Stage stage) {
        this.stage = stage;
    }

    /**
     * 关闭当前web页面窗口
     */
    public void closeWindow() {
        System.out.println("close window...");
        stage.close();
    }

    /**
     * 在java控制台输出js内容
     *
     * @param log
     */
    public void showLog(String log) {
        System.out.println("http log: " + log);
    }

}