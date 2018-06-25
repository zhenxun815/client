package com.tqhy.client.network.api;

import com.tqhy.client.model.bean.ClientMsg;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public interface AiHelperApi {
    @GET("warning/{key}")
    Observable<ResponseBody> requestAiHelper(@Path("key") String key);

    @POST("warningback")
    Observable<ResponseBody> postAiWarning(@Body ApiBean<ClientMsg> warningBack);

    @POST("confirm")
    Observable<ResponseBody> postHistory(@Body ApiBean<ClientMsg> date);

    @GET("warning/{key}")
    Call<ResponseBody> getTest(@Path("key") String key);
}
