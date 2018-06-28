import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;
import sun.rmi.runtime.Log;



/**
 * @author Yiheng
 * @create 2018/6/27
 * @since 1.0.0
 */
public class TestLog {
    @Test
    public void testLog(){
        Logger stdout = Logger.getLogger(TestLog.class);
        stdout.info("test,,");

    }
}
