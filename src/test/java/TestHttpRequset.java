import com.tqhy.client.network.Network;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class TestHttpRequset {
    @Test
    public void testCircleRequest() {
        Network.getAiResultApi()
                .getTest()
                .repeatWhen(objectObservable ->
                        objectObservable.flatMap(o ->
                                Observable.just(1).delay(2000, TimeUnit.MILLISECONDS)))
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(testMsg ->
                        Platform.runLater(() ->
                                System.out.println(testMsg))
                );
    }

    @Test
    public void testHttpRequest() {
        Network.getAiResultApi()
                .getTest()
                .map(testMsg -> testMsg)
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.computation())
                .subscribe(testMsg -> System.out.println(testMsg));
    }
}
