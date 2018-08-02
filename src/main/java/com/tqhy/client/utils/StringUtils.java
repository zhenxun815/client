package com.tqhy.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author Yiheng
 * @create 2018/8/2
 * @since 1.0.0
 */
public class StringUtils {

    public static final String BTJ_REG = "[0-9]{10,}\\$JYSP\\$[0-9]{4}-[0-9]{2}-[0-9]{2}";

    /**
     * 正则判断
     *
     * @param key 需要判断的字符串
     * @param reg 正则表达式
     * @return 匹配返回true, 否则返回false
     */
    public static boolean matchKey(String key, String reg) {
        //reg = "[0-9]{10,}$JYSP$[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(key);
        return matcher.find();
    }
}
