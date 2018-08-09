package com.tqhy.client.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/28
 * @since 1.0.0
 */
public class JnaCaller {

    private static Logger logger = LoggerFactory.getLogger(JnaCaller.class);
    private static String NATIVE_LIB_NAME = "jyTQAITools";

    /**
     * 系统尚未授权
     */
    public static final String FETCH_DATA_LICENSE = "JYLICENSE";
    /**
     * 非RIS窗口,未获取数据
     */
    public static final String FETCH_DATA_NODATA = "JYNODATA";
    /**
     * 连接动态库失败
     */
    public static final String FETCH_DATA_FAILED = "FAILED";

    public static String jniRootPath;

    /**
     * 调用dll中jyFetchData方法
     *
     * @return "JYLICENSE":表示系统尚未被授权;"JYNODATA":表示未获得有效数据;其它:获得的有效 HIS 数据
     */
    public static String fetchData(String imgPath) {
        try {
            //logger.info("into fetchData....");
            // NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
            // Native.register(TqaiDll.class, "jyTQAITools");
            Pointer p = TqaiDll.caller.jyFetchData(imgPath);
            String result = p.getString(0L);
            //logger.info("fetch data success: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("load dll fail..", e);
        }
        return FETCH_DATA_FAILED;
    }

    /**
     * 调用dll中jyGetUserInfo方法
     */
    public static void getUserInfo() {
        try {
            logger.info("into getUserInfo....");
            // NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
            // Native.register(TqaiDll.class, "jyTQAITools");
            TqaiDll.caller.jyGetUserInfo();
        } catch (Throwable e) {
            logger.error("load dll fail..", e);
        }
    }

    /**
     * 调用动态库接口类
     */
    interface TqaiDll extends StdCallLibrary {
        TqaiDll caller = Native.loadLibrary(NATIVE_LIB_NAME, TqaiDll.class);

        Pointer jyFetchData(String imgPath);

        void jyGetUserInfo();
    }
}
