package com.tqhy.client.network.api;

import com.tqhy.client.model.ClientMsg;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

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
    @Multipart
    @POST("ai/helper/warning")
    Observable<ResponseBody> getAiWarning(@Part("key") RequestBody key, @Part() MultipartBody.Part part);

    /**
     * 弹出提示信息后向后台返回否接收到弹窗信息及弹框相关操作
     * @param warningBack
     * @return
     */
    @POST("ai/helper/warningback")
    Observable<ResponseBody> postAiWarningBack(@Body ApiBean<ClientMsg> warningBack);

    /**
     * 向后台发送用户操作历史
     * @param date
     * @return
     */
    @POST("ai/helper/confirm")
    Observable<ResponseBody> postHistory(@Body ApiBean<ClientMsg> date);

    /**
     * 获取当前aiDrId
     * @param key
     * @return
     */
    @GET("ai/helper/aiDrId/{key}")
    Observable<ResponseBody> getAiDrId(@Path("key") String key);

    /**
     * 测试
     * @param key
     * @return
     */
    @GET("ai/helper/warning/{key}")
    Call<ResponseBody> getTest(@Path("key") String key);
}
