import org.apache.log4j.Logger;
import org.junit.Test;


/**
 * @author Yiheng
 * @create 2018/6/27
 * @since 1.0.0
 */
public class TestLog {
    @Test
    public void testLog() {
        Logger stdout = Logger.getLogger(TestLog.class);
        stdout.info("test,,");

    }
}
