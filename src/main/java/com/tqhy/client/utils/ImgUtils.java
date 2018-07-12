package com.tqhy.client.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/7/12
 * @since 1.0.0
 */
public class ImgUtils {

    public static boolean cutImg(String originImgPath, String cuttedImgPath, int x, int y, int width, int height) {
        try {
            File originImg = new File(originImgPath);
            File cuttedImmg = new File(cuttedImgPath);
            if (originImg.exists()) {
                BufferedImage bufferedImage = ImageIO.read(originImg);
                BufferedImage bufferedSubImage = bufferedImage.getSubimage(x, y, width, height);
                return ImageIO.write(bufferedSubImage, "jpg", cuttedImmg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 截取屏幕
     *
     * @param screenImgPath
     * @return
     * @throws Exception
     */
    public static String captureScreen(String screenImgPath) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenImg = new File(screenImgPath);
        // 如果路径不存在,则创建
        if (!screenImg.getParentFile().exists()) {
            screenImg.getParentFile().mkdirs();
        }
        //判断文件是否存在，不存在就创建文件
        if (!screenImg.exists() && !screenImg.isDirectory()) {
            screenImg.mkdir();
        }

        ImageIO.write(image, "jpg", screenImg);
        //自动打开
        /*if (Desktop.isDesktopSupported()
                 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                    Desktop.getDesktop().open(f);*/
        return screenImg.getAbsolutePath();
    }
}
