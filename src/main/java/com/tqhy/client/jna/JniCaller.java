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

    static {
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
        /*String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        int end = jarPath.lastIndexOf("/");
        String rootPath = jarPath.substring(1, end);
        end = rootPath.lastIndexOf("/");
        rootPath = rootPath.substring(0, end);
        jniRootPath = rootPath;
        try {
            NativeLibrary.addSearchPath("jyTQAITools", rootPath);
            Native.register(TqaiDll.class, "jyTQAITools");
            //System.load(rootPath + "/jyTQAITools.dll");
        } catch (Throwable e) {
            Logger exfile = LoggerFactory.getLogger("exfile");
            exfile.error("init jni fail...", e);
        }*/

    }

    private static Logger logger = LoggerFactory.getLogger(JniCaller.class);
    public static String jniRootPath;

    public static String fetchData() {
        try {
            logger.info("into fetchData....");
            NativeLibrary.addSearchPath("jyTQAITools", jniRootPath);
            Native.register(TqaiDll.class, "jyTQAITools");
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

    public static void callSysDll(String str, String... args) {
        SysDll.call.printf(str, args);
    }

    interface TqaiDll extends StdCallLibrary {
        TqaiDll call = Native.loadLibrary("jyTQAITools", TqaiDll.class);

        Pointer jyFetchData();

        void jyGetUserInfo();
    }

    interface SysDll extends StdCallLibrary {
        SysDll call = Native.loadLibrary("msvcrt", SysDll.class);

        void printf(String format, Object... args);
    }
}
