package com.greenhand.game73.okhttp.builder;

import java.util.Map;

/**
 * Created by zhy on 16/3/1.
 *
 * 主要用于
 * get:添加“？”后面的参数
 * post:添加向服务器发送的数据（参数）
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
