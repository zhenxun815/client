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
        String originImgPath = "C:/Users/qing/Desktop/capture.jpg";
        String cuttedImgPath = "C:/Users/qing/Desktop/capture_cutted.jpg";
        ImgUtils.cutImg(originImgPath, cuttedImgPath, 449, 79, 670, 670);
    }
}
