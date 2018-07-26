package com.tqhy.client.network;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/20
 * @since 1.0.0
 */
public class JavaApp {

    public String id = "id";
    private final Stage stage;
    private Logger logger = LoggerFactory.getLogger(JavaApp.class);

    public JavaApp(Stage stage) {
        this.stage = stage;
    }

    /**
     * 关闭当前web页面窗口
     */
    public void closeWindow() {
        logger.info("close window...");
        stage.close();
    }

    /**
     * 在java控制台输出js内容
     *
     * @param log
     */
    public void showLog(String log) {
        logger.info("http log: " + log);
    }

}