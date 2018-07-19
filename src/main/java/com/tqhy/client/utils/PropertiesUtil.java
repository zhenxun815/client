package com.tqhy.client.utils;

import java.io.*;
import java.util.Properties;

/**
 * 操作properties文件
 *
 * @author Yiheng
 */
public class PropertiesUtil {

    /**
     * @param key propertyName
     * @return 返回properties 文件
     */

    public static String getPropertiesKeyValue(String key) {
        Properties properties = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FileUtils.getRootPath() + "/manager.properties"));
            properties = new Properties();
            properties.load(bufferedReader);
            return properties.getProperty(key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    //参数为要修改的文件路径  以及要修改的属性名和属性值
    public static Boolean updatePro(String key, String value) {

        // 文件输出流     
        Properties properties = null;
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(FileUtils.getRootPath() + "/manager.properties"));
            properties = new Properties();
            properties.load(bufferedReader);
            System.out.println("获取添加或修改前的属性值：" + key + "=" + properties.getProperty(key));
            properties.setProperty(key, value);

            FileOutputStream fos = new FileOutputStream(FileUtils.getRootPath() + "/manager.properties");
            // 将Properties集合保存到流中     
            properties.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            fos.close();// 关闭流     
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("获取添加或修改后的属性值：" + key + "=" + properties.getProperty(key));
        return true;
    }

}




