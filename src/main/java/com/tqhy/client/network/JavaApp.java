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
     * 鼠标拖动标题栏实现窗口移动
     *
     * @param screenX 鼠标相对屏幕x坐标
     * @param screenY 鼠标相对屏幕y坐标
     * @param pageX   鼠标相对页面x坐标
     * @param pageY   鼠标相对页面y坐标
     */
    public void moveStage(int screenX, int screenY, int pageX, int pageY) {
        logger.info("screenX: " + screenX + ", screenY: " + screenY);
        logger.info("pageX: " + pageX + ", pageY: " + pageY);
        logger.info("stageX: " + stage.getX() + " ,stageY: " + stage.getY());
        stage.setX(screenX - pageX);
        stage.setY(screenY - pageY);
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