package com.greenhand.game73.okhttp.builder;


import com.greenhand.game73.okhttp.OkHttpUtils;
import com.greenhand.game73.okhttp.request.OtherRequest;
import com.greenhand.game73.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
