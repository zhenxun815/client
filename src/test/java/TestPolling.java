import com.tqhy.client.jna.JnaCaller;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Yiheng
 * @create 2018/6/19
 * @since 1.0.0
 */
public class TestPolling {

    private Logger logger = LoggerFactory.getLogger(TestPolling.class);

    public TestPolling() {
        super();
    }

    @Test
    public void testPolling() {
        Observable.interval(3000, TimeUnit.MICROSECONDS)
                  .doOnNext(l -> {
                      Object i = JnaCaller.fetchData("");
                      logger.info(i + "");
                  })
                  .observeOn(Schedulers.io())
                  .subscribeOn(Schedulers.trampoline())
                  .subscribe(l -> {
                      logger.info("subscribe: " + l);
                  });
    }
}
