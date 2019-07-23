package com.sunlight.string;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StringUtil {

    private static final String COMMA = ",";

    /**
     * 字符串按逗号分割成列表
     * @param str
     * @return
     */
    public static List<String> toList(String str) {
        return toList(str, COMMA);
    }

    public static List<String> toList(String str, String deli) {
        String source = trimOrNull(str);
        List<String> list = new ArrayList<>();
        if (source == null) return list;
        String[] result = fastSplit(source, deli);
        if (result == null) return list;

        for (String s : result) {
            if (s != null && !s.trim().isEmpty()) {
                list.add(s.trim());
            }
        }
        return list;
    }

    /**
     * 字符串按逗号分割成列表，并进行数据转换
     * @param str
     * @param mapper 数据转换函数
     * @return
     */
    public static <R> List<R> toTransformList(String str, Function<? super String, R> mapper) {
        return toTransformList(str, ",", mapper);
    }

    public static <R> List<R> toTransformList(String str,String deli,Function<? super String, R> mapper) {
        String source = trimOrNull(str);
        List<R> list = new ArrayList<>();
        if(source == null) return list;

        String[] result = fastSplit(source, deli);
        if (result == null) return list;

        for (String s : result) {
            if (s != null && !s.trim().isEmpty()) {
                try {
                    R applyResult = mapper.apply(s.trim());
                    list.add(applyResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 对于只包含空白字符的字符串，返回null；非空字符串trim后返回
     * @param str
     * @return
     */
    private static String trimOrNull(String str) {
        return str != null && !str.trim().isEmpty() ? str.trim() : null;
    }

    /**
     * 快速分隔字符串
     * @param src
     * @param delimeter
     * @return
     */
    public static String[] fastSplit(String src,String delimeter){
        String srcStr = src;
        String delimeterStr = delimeter;
        if(srcStr==null){
            return null;
        }
        if(delimeterStr==null){
            throw new IllegalArgumentException("delimeter should not be null");
        }
        if(delimeterStr.equals("")){ //直接返回每个字符的字符串形式
            String[] array = new String[srcStr.length()];
            for(int i = 0;i<array.length;i++){
                array[i] = String.valueOf(srcStr.charAt(i));
            }
            return array;
        }
        if (srcStr.length() > delimeterStr.length()) { //源字符串长度大于分隔符字符串长度
            int i = srcStr.indexOf(delimeterStr);
            int j = i;
            int n = 0;
            int lastIndex = srcStr.length() - delimeterStr.length();
            boolean lastStringIsDelimeter = false;
            while (i >= 0) {
                n++;
                i = srcStr.indexOf(delimeterStr, i + delimeterStr.length());
                if (i == lastIndex) { // delimeter is the last string of the src, should not be counted
                    lastStringIsDelimeter = true;
                    break;
                }
            }
            String[] array = new String[n + 1];
            n = i = 0;
            while (j >= 0) {
                if (j - i > 0) {
                    array[n++] = srcStr.substring(i, j);
                } else if (j - i == 0) { // two delimeter is neighbour
                    array[n++] = "";
                }
                i = j + delimeterStr.length();
                j = srcStr.indexOf(delimeterStr, i);
            }
            if (!lastStringIsDelimeter) {
                array[n] = srcStr.substring(i);
            }
            return array;
        } else if (srcStr.length() == delimeterStr.length()) { // 源字符串长度等于 分隔符字符串长度
            if (srcStr.equals(delimeterStr)) {
                return new String[0];
            } else {
                String[] array = new String[1];
                array[0] = srcStr;
                return array;
            }
        } else { // 源字符串长度 小于 分隔符字符串长度 ， 直接返回源字符串
            String[] array = new String[1];
            array[0] = srcStr;
            return array;
        }
    }


}
