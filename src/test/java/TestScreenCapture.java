import com.tqhy.client.utils.FileUtils;
import com.tqhy.client.utils.ImgUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/7/10
 * @since 1.0.0
 */
public class TestScreenCapture {

    Logger logger = LoggerFactory.getLogger(TestScreenCapture.class);

    @Test
    public void testScreenCapture() {
        String rootPath = FileUtils.getRootPath();
        System.out.println("rootPath is: " + rootPath);
        try {
            ImgUtils.captureScreen(rootPath + "/capture.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCutImg() {
        String originImgPath = "D:/workspace/client/out/production/screen_capture.jpg";
        String cuttedImgPath = "D:/workspace/client/out/production/capture_cutted.jpg";
        String path = ImgUtils.cutImg(originImgPath, cuttedImgPath, 448, 76, 660, 670);
        System.out.println(path);
    }

    @Test
    public void testCaptureExternalScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = ge.getScreenDevices();
        for (GraphicsDevice screenDevice : screenDevices) {
            GraphicsConfiguration config = screenDevice.getDefaultConfiguration();
            Rectangle bounds = config.getBounds();
            logger.info("bounds x: " + bounds.x + " y: " + bounds.y + " width: " + bounds.width + " height: " + bounds.height);
        }
        GraphicsDevice secondDevice = screenDevices[1];
        Rectangle secondBounds = secondDevice.getDefaultConfiguration().getBounds();
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage image = robot.createScreenCapture(secondBounds);

        String screenImgPath = "E:/Users/tqhy/Desktop/capture.jpg";
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

        try {
            ImageIO.write(image, "jpg", screenImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
