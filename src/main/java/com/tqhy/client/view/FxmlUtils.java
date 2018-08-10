package com.tqhy.client.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/6/12
 * @since 1.0.0
 */
public class FxmlUtils {

    /**
     * 为控件对象加载fxml布局文件
     *
     * @param sourceUrl fxml文件地址
     * @param obj       fxml对应控件对象
     */
    public static void load(String sourceUrl, Object obj) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(sourceUrl));
        loader.setRoot(obj);
        loader.setController(obj);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 为控件对象加载fxml布局文件
     *
     * @param sourceUrl  fxml文件地址
     * @param root
     * @param controller fxml对应控件对象
     */
    public static void load(String sourceUrl, Object root, Object controller) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(sourceUrl));
        loader.setRoot(root);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
