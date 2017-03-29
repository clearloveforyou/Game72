package com.greenhand.game73.okhttp.builder;

import android.net.Uri;

import com.greenhand.game73.okhttp.request.GetRequest;
import com.greenhand.game73.okhttp.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable
{
    @Override
    public RequestCall build()
    {
        /**
         * 如果传入的url是完整的，直接return
         * 如果是参数分离的，只执行if循环，从而得到完整的url
         */
        if (params != null)
        {
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers,id).build();
    }

    /**
     * 拼接参数
     * @param url
     * @param params
     * @return
     */
    protected String appendParams(String url, Map<String, String> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();//???不理解
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }


}
