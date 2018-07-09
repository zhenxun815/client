package com.tqhy.client.network.api;

import com.tqhy.client.model.ClientMsg;
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
    /**
     * 获取ai提示弹框提示内容
     * @param key
     * @return
     */
    @GET("warning/{key}")
    Observable<ResponseBody> getAiWarning(@Path("key") String key);

    /**
     * 弹出提示信息后向后台返回否接收到弹窗信息及弹框相关操作
     * @param warningBack
     * @return
     */
    @POST("warningback")
    Observable<ResponseBody> postAiWarningBack(@Body ApiBean<ClientMsg> warningBack);

    /**
     * 向后台发送用户操作历史
     * @param date
     * @return
     */
    @POST("confirm")
    Observable<ResponseBody> postHistory(@Body ApiBean<ClientMsg> date);

    /**
     * 获取当前aiDrId
     * @param key
     * @return
     */
    @GET("aiDrId/{key}")
    Observable<ResponseBody> getAiDrId(@Path("key") String key);

    /**
     * 测试
     * @param key
     * @return
     */
    @GET("warning/{key}")
    Call<ResponseBody> getTest(@Path("key") String key);
}
