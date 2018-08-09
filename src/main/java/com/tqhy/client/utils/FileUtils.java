package com.tqhy.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作相关工具类
 *
 * @author Yiheng
 * @create 2018/7/10
 * @since 1.0.0
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取项目所在路径
     *
     * @return
     */
    public static String getRootPath() {
        String jarPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        int end = jarPath.lastIndexOf("/");
        String rootPath = jarPath.substring(1, end);
        end = rootPath.lastIndexOf("/");
        rootPath = rootPath.substring(0, end);
        //logger.info("rootPath is: "+rootPath);
        return rootPath;
    }

    /**
     * 获取jar包所在路径
     *
     * @return
     */
    public static String getJarPath() {
        String jarPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        //logger.info("jarPath is: "+jarPath);
        return jarPath;
    }
}
