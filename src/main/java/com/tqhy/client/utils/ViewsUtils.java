package com.tqhy.client.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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


    public static String captureScreen(String fileName, String folder) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenFile = new File(folder,fileName);
        // 如果路径不存在,则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }
        //判断文件是否存在，不存在就创建文件
        if(!screenFile.exists()&& !screenFile .isDirectory()) {
            screenFile.mkdir();
        }

        ImageIO.write(image, "jpg", screenFile);
        //自动打开
        /*if (Desktop.isDesktopSupported()
                 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                    Desktop.getDesktop().open(f);*/
        return screenFile.getAbsolutePath();
    }
}
