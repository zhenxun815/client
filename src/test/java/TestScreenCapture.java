import com.tqhy.client.utils.FileUtils;
import com.tqhy.client.utils.ImgUtils;
import org.junit.Test;

/**
 * @author Yiheng
 * @create 2018/7/10
 * @since 1.0.0
 */
public class TestScreenCapture {

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
}
