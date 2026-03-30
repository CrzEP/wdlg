package com.dlg.wdlg.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient();

    public static String buildGetUrl(String baseUrl, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(baseUrl);
        if (!params.isEmpty()) {
            sb.append("?");
            params.forEach((key, value) -> {
                try {
                    sb.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(value, StandardCharsets.UTF_8))
                            .append("&");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            sb.setLength(sb.length() - 1); // 去掉最后一个 &
        }
        return sb.toString();
    }

    public static String get(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("HTTP error: " + response.code());
            }
            return response.body() != null ? response.body().string() : null;
        }
    }
}