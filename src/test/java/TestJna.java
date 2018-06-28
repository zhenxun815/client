import com.tqhy.client.jna.JnaTest;
import org.junit.Test;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public class TestJna {
    @Test
    public void testJna(){
        int i = JnaTest.caller.jyTestFunc(2, 3);
        logger.info(i);
    }

    @Test
    public void testGetPath(){
        String classPath = TestJna.class.getClassLoader().getResource("").getPath();
        logger.info("path is: "+classPath);
    }
}
