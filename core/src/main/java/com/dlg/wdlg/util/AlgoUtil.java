package com.dlg.wdlg.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 算法工具
 */
public class AlgoUtil {

    /**
     * 随机数生成器
     */
    private final static Random RANDOM = new Random(System.nanoTime());


    /**
     * md5摘要算法
     * 此算法不安全
     *
     * @param input 输入参数
     * @return 输出
     */
    public static String md5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(java.lang.String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 安全的 hash 算法 sha256
     *
     * @param input 输入参数
     * @return 输出
     */
    public static String sha256(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input);
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取随机盐
     *
     * @return 盐字符
     */
    public static String getRandom(int length) {
        byte[] saltBytes = new byte[length];
        RANDOM.nextBytes(saltBytes);
        return new String(saltBytes, StandardCharsets.UTF_8);
    }

}
