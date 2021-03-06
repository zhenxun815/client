import com.tqhy.client.utils.MD5Utils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/7/12
 * @since 1.0.0
 */
public class TestUtils {
    Logger logger = LoggerFactory.getLogger(TestUtils.class);

    @Test
    public void testGetMd5() {
        String originStr = "5b4bb18bd9a84bdf9a18971a7b82b7bf";
        String md5 = MD5Utils.getMD5(originStr);
        logger.info("md5 is: " + md5);
    }
}
