package com.tqhy.client.utils;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 界面相关工具类
 *
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class ViewsUtils {

    public static final String SHOW_VIEW_TAG = "show_view_tag";
    public static final String CLOSE_VIEW_TAG = "close_view_tag";
    private static Logger logger = LoggerFactory.getLogger(ViewsUtils.class);

    /**
     * 获取屏幕最大可用宽度
     *
     * @return 屏幕最大可用宽度
     */
    public static double getScreenWidth() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        //return visualBounds.getWidth();
        return visualBounds.getMaxX();
    }

    /**
     * 获取窗口左上角x坐标最大值
     *
     * @param width 窗口宽度
     * @return 窗口左上角横坐标位置
     */
    public static double getMaxX(double width) {
        return getScreenWidth() - width;
    }

    /**
     * 获取屏幕最大可用高度
     *
     * @return 屏幕最大可用高度
     */
    public static double getScreenHeight() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        //return visualBounds.getHeight();
        return visualBounds.getMaxY();
    }

    /**
     * 获取窗口左上角Y坐标最大值
     *
     * @param height
     * @return
     */
    public static double getMaxY(double height) {
        return getScreenHeight() - height;
    }

    public static void stageShowingAnimation(Stage stage) {
        stage.setOnShowing(event -> {
            logger.info("into onShown...");
            Pane pane = new Pane();
            TranslateTransition transTrans = new TranslateTransition(Duration.millis(500), pane);
            double screenWidth = ViewsUtils.getScreenWidth();
            double screenHeight = ViewsUtils.getScreenHeight();
            double originWidth = screenWidth / 3;
            double originHeight = 4 * screenHeight / 5;
            stage.setWidth(originWidth);
            stage.setHeight(originHeight);
            transTrans.setFromX(screenWidth - originWidth);
            transTrans.setFromY(-originWidth);
            transTrans.setToX(screenWidth - originWidth);
            transTrans.setToY(0);
            pane.translateXProperty()
                .addListener((observable, oldValue, newValue) -> {
                    stage.setX(newValue.doubleValue());
                });
            pane.translateYProperty()
                .addListener((observable, oldValue, newValue) -> {
                    stage.setY(newValue.doubleValue());
                });

            ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(500), pane);
            scaleTrans.setFromX(0.2D);
            scaleTrans.setToX(1.0D);

            pane.scaleXProperty()
                .addListener((observable, oldValue, newValue) -> {
                    logger.info("newValue is: " + newValue);
                    logger.info("stage width is: " + stage.getWidth());
                    //this.setWidth(newValue.doubleValue() * originWidth);
                    stage.setWidth(newValue.doubleValue() * originWidth);
                });


            ParallelTransition paralTrans = new ParallelTransition(pane, transTrans);
            paralTrans.play();
            //stage.setResizable(false);

            logger.info("play finish...");
        });
    }

}
