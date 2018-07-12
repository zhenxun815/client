import com.tqhy.client.utils.FileUtils;
import com.tqhy.client.utils.ViewsUtils;
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
        System.out.println("rootPath is: "+rootPath);
        try {
            ViewsUtils.captureScreen( "capture.jpg", rootPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
