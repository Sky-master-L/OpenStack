package com.lrj.openstack.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: StrUtils
 * Description: 字符串操作工具类
 * Date: 2021/8/18 15:03
 *
 * @author luorenjie
 * @version 1.0
 * @since JDK 1.8
 */
public class StrUtils {
    private static final String NUMBER_CHAR = "0123456789";
    private static final String SPECIAL_CHAR = "@#.&*";
    private static final String LETTER_UPPER_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTER_LITTLE_CHAR = "abcdefghijklmnopqrstuvwxyz";


    /**
     * 获取指定字符串和指定长度随机字符串
     *
     * @param s
     * @param length
     * @return
     */
    public static String generateRandomLengthStr(String s, int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(s.charAt(random.nextInt(s.length())));
        }
        return sb.toString();
    }

    /**
     * 将指定字符串随机排列
     *
     * @param s
     * @return
     */
    public static String generateRandomStr(String s) {
        List<String> stringList = Arrays.asList(s.split(""));
        Collections.shuffle(stringList);
        StringBuffer sb = new StringBuffer();
        for (String s1 : stringList) {
            sb.append(s1);
        }
        return sb.toString();
    }

    /**
     * 生成8位复杂的windows密码
     *
     * @return
     */
    public static String generateWinPass() {
        String upStr = generateRandomLengthStr(LETTER_UPPER_CHAR, 2);
        String liStr = generateRandomLengthStr(LETTER_LITTLE_CHAR, 2);
        String nuStr = generateRandomLengthStr(NUMBER_CHAR, 2);
        String spStr = generateRandomLengthStr(SPECIAL_CHAR, 2);
        String combineStr = upStr + liStr + nuStr + spStr;
        String s = generateRandomStr(combineStr);
        return s;
    }

    /**
     * 处理为数字集合的字符串，并返回数字集合
     *
     * @param str
     * @return
     */
    public static List<Integer> greatedIntegerList(String str) {
        List<Integer> numList = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            return numList;
        }
        String s = str.replaceAll("\\[", "").replaceAll("\\]", "");
        if (ObjectUtils.isEmpty(s)) {
            return numList;
        }
        String[] ss = s.split(",");
        for (int i = 0; i < ss.length; i++) {
            numList.add(Integer.valueOf(ss[i]));
        }
        return numList;
    }

    /**
     * 根据固定字符串和长度生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        String combineStr = NUMBER_CHAR + LETTER_UPPER_CHAR + LETTER_LITTLE_CHAR;
        for (int i = 0; i < length; i++) {
            sb.append(combineStr.charAt(random.nextInt(combineStr.length())));
        }
        return sb.toString();
    }

    /**
     * 数字字符串排序，并返回
     *
     * @param str
     * @return
     */
    public static String sortStringIntegerList(String str) {
        List<Integer> list = greatedIntegerList(str);
        list = list.stream().sorted().collect(Collectors.toList());
        String ss = list.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "");
        return ss;
    }

    public static void main(String[] args) {
        List<Integer> virtualIds = new ArrayList<>(Arrays.asList(1,2,3));
        String vids = virtualIds.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "");
        System.out.println(vids);
    }
}
