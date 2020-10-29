package com.myweatherforecast.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class HttpUtil {
    /**
     * 该类是用来和服务器进行交互的
     * <p>
     * 现在我们发起一条http请求只需要调用sendOkHttpRequest()方法
     * 传入请求地址
     * 并注册一个回调处理服务器响应就可以了
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
