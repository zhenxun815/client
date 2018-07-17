package com.tqhy.client.network;


import com.tqhy.client.network.api.AiHelperApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
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
    public static String currentId = "";

    public static final String TEST_URL = "http://baidu.com/";
    public static String IP;
    public static String BASE_URL;

    public static final String HISTORY_PAGE = "history";
    public static final String REPORT_PAGE = "report";
    public static final String AI_PROMPT_PAGE = "ai_prompt";

    private static Logger logger = LoggerFactory.getLogger(Network.class);
    /**
     * 获取AIHelperApi对象
     *
     * @return
     */
    public static AiHelperApi getAiHelperApi() {

        //logger.info("into getAiHelperApi..");
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

    /**
     * 根据待上传文件路径生成上传文件{@link MultipartBody.Part}对象
     *
     * @param filePath
     * @return
     */
    public static MultipartBody.Part createMultipart(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return part;
    }

    /**
     * 将字符串转换为{@link RequestBody}对象
     *
     * @param content
     * @return
     */
    public static RequestBody createRequestBody(String content) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), content);
        return body;
    }

    public static void setBaseUrl(String ip) {
        BASE_URL = "http://" + ip + ":8080/ai/helper/";
    }
}
