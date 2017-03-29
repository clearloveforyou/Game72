package com.greenhand.game73.okhttp.request;


import com.greenhand.game73.okhttp.callback.Callback;
import com.greenhand.game73.okhttp.utils.Exceptions;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest
{
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;
    //okhttp requset的建造者
    protected Request.Builder builder = new Request.Builder();

    /**
     * 构造器
     * @param url
     * @param tag
     * @param params
     * @param headers
     * @param id
     */
    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers,int id)
    {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id ;

        /**
         * url为空，抛出自定义异常："url can not be null."
         */
        if (url == null)
        {
            Exceptions.illegalArgument("url can not be null.");
        }

        initBuilder();
    }



    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder()
    {
        //开始调用okhttp的接口
        builder.url(url).tag(tag);
        appendHeaders();
    }

    /**
     * 这个抽象方法的设计：针对不同的请求：
     * get:没有请求体，实现类返回null
     * post:
     *      表单上传：返回一个FormBody
     *      JsonStr: 返回一个RequestBody   、、、、、RequestBody.create()
     *      文件上传：multipartBody
     * @return
     */
    protected abstract RequestBody buildRequestBody();

    /**
     * 一个方法设为protected一般就是为了让子类重写
     * 这个方法的设计：
     *         重写RequestBody的write to()方法，计算文件（json字符串）上传的进度，设一个接口将进度回调出来
     *         再用callback的inProgress（）来做处理：比如设一个进度条
     * 这方法就是为了处理Requestbody: 来显示上传的一些进度
     *
     * @param requestBody
     * @param callback
     * @return
     */
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback)
    {
        return requestBody;
    }

    /**
     * 调用builder的build()方法，直接构建出Request;
     * @param requestBody
     * @return
     */
    protected abstract Request buildRequest(RequestBody requestBody);

    /**
     *
     * @return
     */
    public RequestCall build()
    {
        return new RequestCall(this);
    }

    /**
     * 获取到我们构建好的Request
     * @param callback
     * @return
     */
    public Request generateRequest(Callback callback)
    {
        RequestBody requestBody = buildRequestBody();//用于表单的提交（get请求时直接返回null）得到FromBody
        /**
         * get:传进来的与返回的一致
         * post:只是对上传的进度加了一个接口，便于我们设置进度条
         * postJsonStr:传进来与返回一致
         *
         */
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    /**
     * 将传入的请求头处理
     */
    protected void appendHeaders()
    {
        //okhttp处理请求头的接口
        Headers.Builder headerBuilder = new Headers.Builder();

        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet())
        {
            headerBuilder.add(key, headers.get(key));
        }
        //调用okhttp的接口，添加请求头
        builder.headers(headerBuilder.build());
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return id  ;
    }

}
