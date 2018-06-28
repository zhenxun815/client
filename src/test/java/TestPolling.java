import com.tqhy.client.jna.JnaTest;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yiheng
 * @create 2018/6/19
 * @since 1.0.0
 */
public class TestPolling {
    @Test
    public void testPolling() {
        Observable.interval(3000, TimeUnit.MICROSECONDS)
                .doOnNext(l -> {
                    int i = JnaTest.caller.jyTestFunc(2, 3);
                    logger.info(i);
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(l -> {
                    logger.info("subscribe: " + l);
                });
    }
}
