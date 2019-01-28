package cn.luutqf.rancher.client.utils;

import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ZhenYang
 * @date: 2019/1/25
 * @description:
 */
public class LogsUtil {

    public static LinkedHashMap<String,String> tokenMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,String> logMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,StringBuffer> logMap2 = new LinkedHashMap<>();
    public static Function<String,Boolean> getTokenByRegex = s -> {
        String key;
        Pattern p = Pattern.compile("token=.?\\S+");
        Pattern p2 = Pattern.compile("http://\\((.*?)\\s+");
        Matcher m = p2.matcher(s);
        if (m.find()) {
            key = m.group(1);
            m = p.matcher(s);
            if (m.find()) {
                LogsUtil.tokenMap.put(key, m.group(0));
                return true;
            }
        }
        return false;
    };

    public static Function<String,Boolean> getTokenByRegex2 = (k) ->{
        String s = LogsUtil.logMap2.get(k).toString();
        Pattern p = Pattern.compile("token=.?\\S+");
        Matcher m = p.matcher(s);
        if (m.find()) {
            LogsUtil.tokenMap.put(k, m.group(0));
            return true;
        }
        return false;
    };

    public static Function<String,String> getTokenByRegex3 = s ->{
        Pattern p = Pattern.compile("token=.?\\S+");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group(0);
        }
        return "";
    };

    public static Function<String,Boolean> getLogs = s -> true;

}
