package com.tqhy.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author Yiheng
 * @create 2018/8/9
 * @since 1.0.0
 */
public class StringUtils {

    public static final String BTJ_REG = "[0-9]{10,}\\$JYSP\\$[0-9]{4}/[0-9]{1,}/[0-9]{1,}";
    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

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

    /**
     * 解析日期字符串
     *
     * @param dateString 待解析字符串
     * @param split      字符创中分隔符
     * @return
     */
    public static String formatDateString(String dateString, String split) {
        String parsedDateStr = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy" + split + "MM" + split + "dd");
            Date date = format1.parse(dateString);

            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
            parsedDateStr = format2.format(date);
            logger.info("parsedDateStr: " + parsedDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDateStr;
    }
}
