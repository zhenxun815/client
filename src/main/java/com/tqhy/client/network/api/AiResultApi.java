package com.tqhy.client.network.api;

import com.tqhy.client.model.bean.TestMsg;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public interface AiResultApi {
    @GET("list/{userId}")
    Observable<String> getAiResult(@Path("userId") String userId);

    @GET("list")
    Observable<TestMsg> getTest();
}
