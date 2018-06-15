package com.tqhy.client.network;

import com.tqhy.client.network.api.AiResultApi;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class Network {
    private static AiResultApi aiResultApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static final String BASE_URL = "http://192.168.1.244:8887/api/";
    //private static final String BASE_URL = "http://192.168.1.218:7000/";

    public static AiResultApi getAiResultApi() {
        if (null == aiResultApi) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            aiResultApi = retrofit.create(AiResultApi.class);
        }
        return aiResultApi;
    }
}
