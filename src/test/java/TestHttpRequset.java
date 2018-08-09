import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.AiHelperApi;
import com.tqhy.client.utils.FileUtils;
import com.tqhy.client.utils.PropertiesUtil;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.*;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class TestHttpRequset {

    private Logger logger = LoggerFactory.getLogger(TestHttpRequset.class);

    @Test
    public void testCircleRequest() {
        //http:192.168.1.139:8080/ai/helper/warning/6d212354a62b48b1aab6c069e2006731
        String cuttedImgPath = "C:/Users/qing/Desktop/capture_cutted.jpg";
        File file = new File(cuttedImgPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "c4cd996cec662fb2fa7e67770ac2ed78");
        Network.getAiHelperApi()
               .getAiWarning(key, part)
               .map(body -> {
                   logger.info(body.string());
                   return body.string();
               })
               .observeOn(Schedulers.trampoline())
               .subscribeOn(Schedulers.trampoline())
               .subscribe(str -> logger.info(str));
    }

    @Test
    public void testHttpRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
        CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
        String BASE_URL = "http://localhost:8080/ai/helper/";

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        AiHelperApi aiHelperApi = retrofit.create(AiHelperApi.class);
        Call<ResponseBody> test = aiHelperApi.getTest("c4cd996cec662fb2fa7e67770ac2ed78");
        test.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    logger.info(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Test
    public void testGetLocalIp() {
        try {
            byte[] address = InetAddress.getLocalHost().getAddress();
            logger.info("address.length" + address.length);
            for (byte num : address) {
                logger.info("num: " + (num & 0xff));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetIp() {
        String rootPath = FileUtils.getRootPath();
        System.out.println("rootpath is: " + rootPath);
        String ip = PropertiesUtil.getPropertiesKeyValue("ip");
        System.out.println("ip is:" + ip);
        Network.IP = ip;
        Network.setBaseUrl(ip);
        System.out.println(Network.BASE_URL);
    }
}
