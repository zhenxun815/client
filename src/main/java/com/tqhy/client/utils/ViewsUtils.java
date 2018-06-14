package com.tqhy.client.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * 界面相关工具类
 *
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class ViewsUtils {

    /**
     * 获取屏幕最大可用宽度
     *
     * @return 屏幕最大可用宽度
     */
    public static double getScreenWidth() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
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
}
