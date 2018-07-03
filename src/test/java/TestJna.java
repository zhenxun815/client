import com.tqhy.client.jna.JniCaller;
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
        CharSequence o = JniCaller.fetchData();
        logger.info("testFetchData..." + o);
    }


    @Test
    public void testGetPath() {
        String classPath = TestJna.class.getClassLoader().getResource("").getPath();
        logger.info("path is: " + classPath);
    }
}
