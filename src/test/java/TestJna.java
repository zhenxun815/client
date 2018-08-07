import com.tqhy.client.jna.JnaCaller;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public class TestJna {

    private Logger logger = LoggerFactory.getLogger(TestJna.class);

    @Test
    public void testFetchData() {
        String str = JnaCaller.fetchData("D:/capture.jpg");
        logger.info("testFetchData..." + str);
        int secondIndex = str.lastIndexOf("$");
        String subStr2 = str.substring(secondIndex + 1);
        //StringUtils.formatDateString(subStr2)
    }


    @Test
    public void testGetPath() {
        String classPath = TestJna.class.getClassLoader().getResource("").getPath();
        logger.info("path is: " + classPath);
    }
}
