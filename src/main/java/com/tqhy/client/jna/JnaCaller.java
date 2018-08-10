package com.tqhy.client.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.tqhy.client.utils.PropertiesUtil;
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

    public static int idX;
    public static int idY;
    public static int idWidth;
    public static int idHeight;

    public static int dateX;
    public static int dateY;
    public static int dateWidth;
    public static int dateHeight;

    static {
        idX = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("idX"));
        idY = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("idY"));
        idWidth = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("idWidth"));
        idHeight = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("idHeight"));

        dateX = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("dateX"));
        dateY = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("dateY"));
        dateWidth = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("dateWidth"));
        dateHeight = Integer.parseInt(PropertiesUtil.getPropertiesKeyValue("dateHeight"));
    }

    /**
     * 调用dll中jyFetchData方法
     *
     * @return "JYLICENSE":表示系统尚未被授权;"JYNODATA":表示未获得有效数据;其它:获得的有效 HIS 数据
     */
    public static String fetchData(String imgPath) {
        try {
            logger.info("into fetchData....");
            // NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
            // Native.register(TqaiDll.class, "jyTQAITools");
            logger.info("idx: " + idX + " idy: " + idY + " idwidth: " + idWidth + " idheight: " + idHeight);

            Pointer p1 = TqaiDll.caller.jyFetchDataEx(imgPath, idX, idY, idWidth, idHeight);
            String result1 = p1.getString(0L);
            logger.info("fetch data success: " + result1);

            logger.info("dateX: " + dateX + " dateY: " + dateY + " dateWidth: " + dateWidth + " dateHeight: " + dateHeight);
            Pointer p2 = TqaiDll.caller.jyFetchDataEx(imgPath, dateX, dateY, dateWidth, dateHeight);
            String result2 = p2.getString(0L);
            logger.info("fetch data success: " + result2);
            return result2;
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

        Pointer jyFetchDataEx(String imgPath, int x, int y, int width, int height);

        void jyGetUserInfo();
    }
}
