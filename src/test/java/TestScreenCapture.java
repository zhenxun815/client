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
        String originImgPath = "C:/Users/qing/Desktop/rev/测试影像/立位腹平片.jpg";
        String cuttedImgPath = "C:/Users/qing/Desktop/capture_cutted.jpg";
        String path = ImgUtils.cutImg(originImgPath, cuttedImgPath, 0, 0, 1196, 1600);
        System.out.println(path);
    }
}
