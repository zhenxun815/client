import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.tqhy.client.model.bean.ImgXml;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiheng
 * @create 2018/7/5
 * @since 1.0.0
 */
public class TestAreaCount {

    String boxStr = "[{\"score\":97.36036658287048,\"width\":445,\"x\":1619,\"name\":\"活动\",\"y\":486,\"height\":495},{\"score\":97.30396866798401,\"width\":615,\"x\":561,\"name\":\"活动\",\"y\":322,\"height\":663},{\"score\":83.53266716003418,\"width\":554,\"x\":1575,\"name\":\"活动\",\"y\":628,\"height\":394}]";

    @Test
    public void testCountAreaList() {
        Type type = new TypeToken<ArrayList<ImgXml>>() {
        }.getType();
        List<ImgXml> list = new Gson().fromJson(boxStr, type);
        for (int i = 0; i < list.size() - 1; i++) {
            ImgXml box0 = list.get(i);
            Double b0x = box0.getX();
            Double b0width = box0.getWidth();
            Double b0y = box0.getY();
            Double b0height = box0.getHeight();
            System.out.println("box0: " + box0 + " index: " + i);
            for (int j = i + 1; j < list.size(); j++) {
                ImgXml box1 = list.get(j);
                Double b1x = box1.getX();
                Double b1width = box1.getWidth();
                Double b1y = box1.getY();
                Double b1height = box1.getHeight();
                System.out.println("box1: " + box1 + " index: " + j);
                double diffX = Math.min(b0x + b0width, b1x + b1width) - Math.max(b0x, b1x);
                double diffY = Math.min(b0y + b0height, b1y + b1height) - Math.max(b0y, b1y);

                if (diffX < 0 || diffY < 0) {
                    System.out.println("diffX < 0 || diffY < 0");
                    continue;
                } else {
                    double percent = diffX * diffY / (b0width * b0height);
                    System.out.println("交叉面积占比:" + percent);
                    if (0.5d < percent) {
                        list.remove(j--);
                        System.out.println("list size is: " + list.size());
                    }
                }
            }
        }
    }
}
