package com.dlg.wdlg.compant;

import com.dlg.wdlg.exception.BusinessException;
import com.dlg.wdlg.util.AlgoUtil;
import com.dlg.wdlg.util.HttpUtil;
import com.dlg.wdlg.util.JsonUtils;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
public class BaiduTranslateRest {

    /**
     * 百度翻译地址
     */
    private final static String URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    /**
     * appid 和 密钥
     */
    private static String APP_ID;
    private static String SECRET_KEY;

    /**
     * 单例
     */
    @Getter
    private static volatile BaiduTranslateRest INSTANCE;

    private BaiduTranslateRest(String appID, String secretKey) {
        APP_ID = appID;
        SECRET_KEY = secretKey;
    }

    /**
     * 获得对象
     *
     * @param appID     appID
     * @param secretKey secretKey
     * @return 翻译对象
     */
    public static BaiduTranslateRest INSTANCE(String appID, String secretKey) {
        if (INSTANCE == null) {
            synchronized (BaiduTranslateRest.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaiduTranslateRest(appID, secretKey);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 翻译
     *
     * @param word 单词/句子
     * @return 响应
     */
    public String translate(String word) {
        checkParams();
        String salt = AlgoUtil.getRandom(32);
        String sign = md5(word, salt);
        ReqParam reqParam = new ReqParam(word, salt, sign);
        Map<String, String> map = JsonUtils.beanToMap(reqParam);
        String reqUrl = HttpUtil.buildGetUrl(URL, map);
        try {
            String result = HttpUtil.get(reqUrl);
            log.info(result);
            Result fromJson = JsonUtils.fromJson(result, Result.class);
            return fromJson.getTransResult().get(0).getDst();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 检查参数
     */
    private void checkParams() {
        if (StringUtils.isBlank(APP_ID) || StringUtils.isBlank(SECRET_KEY)) {
            throw new BusinessException("baici translate 组件未初始化...");
        }
    }

    /**
     * md5
     *
     * @param question 带翻译原文
     * @param salt     加盐值
     * @return md5值
     */
    private String md5(String question, String salt) {
        byte[] data = merge(APP_ID, question);
        data = merge(data, salt.getBytes(StandardCharsets.UTF_8));
        data = merge(data, SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return AlgoUtil.md5(data);
    }

    /**
     * 组合
     *
     * @param arrBytes  arrBytes
     * @param nextBytes nextBytes
     * @return 合并数组
     */
    private static byte[] merge(byte[] arrBytes, byte[] nextBytes) {
        byte[] result = new byte[arrBytes.length + nextBytes.length];
        System.arraycopy(arrBytes, 0, result, 0, arrBytes.length);
        System.arraycopy(nextBytes, 0, result, arrBytes.length, nextBytes.length);
        return result;
    }

    /**
     * 组合
     *
     * @param arr  arr
     * @param next next
     * @return 合并数组
     */
    private static byte[] merge(String arr, String next) {
        byte[] arrBytes = arr.getBytes(StandardCharsets.UTF_8);
        byte[] nextBytes = next.getBytes(StandardCharsets.UTF_8);
        return merge(arrBytes, nextBytes);
    }



    /**
     * 请求参数，目前只有中译英需求
     */
    @Data
    static class ReqParam {
        String q;
        String from;
        String to;
        String appid;
        String salt;
        String sign;

        public ReqParam(String q, String appid, String sign) {
            this.q = q;
            this.from = "en";
            this.to = "zh";
            this.appid = appid;
            this.salt = getSalt();
            this.sign = sign;
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
