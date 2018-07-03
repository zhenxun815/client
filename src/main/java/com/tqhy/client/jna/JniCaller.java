package com.tqhy.client.jna;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/28
 * @since 1.0.0
 */
public class JniCaller {

    /*static {
        Logger exfile1 = LoggerFactory.getLogger("exfile");
        String libPath = System.getProperty("java.library.path");
        String[] split = libPath.split(";");
        String exePath = split[0];
        exfile1.info("libPath is: " + libPath);
        //System.out.println("libPath is: " + libPath);
        exfile1.info("exePath is: " + exePath);
        //System.out.println("exePath is: " + exePath);
        jniRootPath = exePath;

        //System.loadLibrary("DjyTQAITools");
    }*/

    private static Logger logger = LoggerFactory.getLogger(JniCaller.class);
    public static String jniRootPath;

    /**
     * 调用dll中jyFetchData方法
     * @return "JYLICENSE":表示系统尚未被授权;"JYNODATA":表示未获得有效数据;其它:获得的有效 HIS 数据
     */
    public static String fetchData() {
        try {
            logger.info("into fetchData....");
           // NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
           // Native.register(TqaiDll.class, "jyTQAITools");
            logger.info("jniRootPath: " + jniRootPath);
            Pointer i = TqaiDll.call.jyFetchData();
            String result = i.getString(0L);
            logger.info("fetch data success: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("load dll fail..", e);
            logger.info("exePth is.." + jniRootPath);
        }
        return "fetch data fail..";
    }

    /**
     * 调用dll中jyGetUserInfo方法
     */
    public static void getUserInfo() {
        try {
            logger.info("into fetchData....");
            // NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
            // Native.register(TqaiDll.class, "jyTQAITools");
            logger.info("jniRootPath: " + jniRootPath);
            TqaiDll.call.jyGetUserInfo();
        } catch (Throwable e) {
            logger.error("load dll fail..", e);
            logger.info("exePth is.." + jniRootPath);
        }
    }

    interface TqaiDll extends StdCallLibrary {
        TqaiDll call = Native.loadLibrary("jyTQAITools", TqaiDll.class);

        Pointer jyFetchData();

        void jyGetUserInfo();
    }
}
