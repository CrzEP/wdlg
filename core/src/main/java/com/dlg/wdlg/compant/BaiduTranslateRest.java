package com.dlg.wdlg.compant;

import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.util.HttpUtil;
import com.dlg.wdlg.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@Slf4j
public class BaiduTranslateRest {

    @Resource
    RestTemplate restTemplate;

    private final static String url = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    private final static String appId = "";
    private final static String secretKey = "";

    private final static Random random = new Random(System.currentTimeMillis());

    private static String md5(String question, String salt) {
        byte[] data = merge(appId, question);
        data = merge(data, salt.getBytes(StandardCharsets.UTF_8));
        data = merge(data, secretKey.getBytes(StandardCharsets.UTF_8));
        return md5(data);
    }

    private static byte[] merge(byte[] arrBytes, byte[] nextBytes) {
        byte[] result = new byte[arrBytes.length + nextBytes.length];
        System.arraycopy(arrBytes, 0, result, 0, arrBytes.length);
        System.arraycopy(nextBytes, 0, result, arrBytes.length, nextBytes.length);
        return result;
    }

    private static byte[] merge(String arr, String next) {
        byte[] arrBytes = arr.getBytes(StandardCharsets.UTF_8);
        byte[] nextBytes = next.getBytes(StandardCharsets.UTF_8);
        return merge(arrBytes, nextBytes);
    }

    private static String md5(byte[] input) {
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
     * 翻译
     *
     * @param word 单词/句子
     * @return 响应
     */
    public static String translate(String word) {
        ReqParam reqParam = new ReqParam(word);
        Map<String, String> map = JsonUtils.beanToMap(reqParam);
        String reqUrl = HttpUtil.buildGetUrl(url, map);
        try {
            String result = HttpUtil.get(reqUrl);
            log.info(result);
            Result fromJson = JsonUtils.fromJson(result, Result.class);
            return fromJson.getTransResult().get(0).getDst();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }


    @Data
    static class ReqParam {
        String q;
        String from;
        String to;
        String appid;
        String salt;
        String sign;

        public ReqParam(String q) {
            this.q = q;
            this.from = "en";
            this.to = "zh";
            this.appid = appId;
            byte[] saltBytes = new byte[32];
            random.nextBytes(saltBytes);
            this.salt = new String(saltBytes, StandardCharsets.UTF_8);
            this.sign = md5(q, salt);
        }
    }

    @Data
    static class Result {
        String from;
        String to;
        Integer errorCode;
        List<TransResult> transResult;
    }

    @Data
    static class TransResult {

        String src;
        String dst;
    }
}
