package com.tqhy.client.network;


import com.tqhy.client.network.api.AiHelperApi;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class Network {
    private static AiHelperApi aiHelperApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    public static String currentId = "0026086fd6654dbfb3d2a3e78cf67140";

    public static final String TEST_URL = "http://baidu.com/";
    public static final String BASE_URL = "http://192.168.1.189:8080/ai/helper/";

    public static final String HISTORY_PAGE = "history";
    public static final String REPORT_PAGE = "report";
    public static final String AI_PROMPT_PAGE = "ai_prompt";

    /**
     * 获取AIHelperApi对象
     *
     * @return
     */
    public static AiHelperApi getAiHelperApi() {
        if (null == aiHelperApi) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            aiHelperApi = retrofit.create(AiHelperApi.class);
        }
        return aiHelperApi;
    }

    /**
     * 获取本地ip
     *
     * @return 本地ip字符串
     */
    public static String getLocalIp() {
        String ip = null;
        try {
            byte[] addr = InetAddress.getLocalHost().getAddress();
            ip = (addr[0] & 0xff) + "." + (addr[1] & 0xff) + "." + (addr[2] & 0xff) + "." + (addr[3] & 0xff);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
