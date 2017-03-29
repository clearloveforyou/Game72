package com.greenhand.game73.okhttp.request;


import com.greenhand.game73.okhttp.OkHttpUtils;
import com.greenhand.game73.okhttp.callback.Callback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/15.
 * 对OkHttpRequest的封装，对外提供更多的接口：cancel(),readTimeOut()...
 *
 * //创建出call任务
 *
 */
public class RequestCall
{
    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    private OkHttpClient clone;

    public RequestCall(OkHttpRequest request)
    {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut)
    {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut)
    {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut)
    {
        this.connTimeOut = connTimeOut;
        return this;
    }

    /**
     * 获取call任务喽-----
     * @param callback
     * @return
     */
    public Call buildCall(Callback callback)
    {

        //至此，得到了okhttp的Requset
        request = generateRequest(callback);

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0)
        {
            //此处对每个请求设置超时，如果没有在application中配置，则取我们给的默认的设置
            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

            clone = OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)//调用okhttp的接口设置超时
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            call = clone.newCall(request);
        } else
        {
            //果果我们没有对本次请求设置，那么就会调用我们在application中给的超时设置，如果灭有在application中
            //设置，那么我们就用okhttp默认给的超时
            call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(Callback callback)
    {
        return okHttpRequest.generateRequest(callback);
    }

    /**
     * 调用此方法
     * @param callback
     */
    public void execute(Callback callback)
    {
        buildCall(callback);

        if (callback != null)
        {
            callback.onBefore(request, getOkHttpRequest().getId());
        }

        OkHttpUtils.getInstance().execute(this, callback);
    }

    public Call getCall()
    {
        return call;
    }

    public Request getRequest()
    {
        return request;
    }

    public OkHttpRequest getOkHttpRequest()
    {
        return okHttpRequest;
    }

    public Response execute() throws IOException
    {
        buildCall(null);
        return call.execute();
    }

    public void cancel()
    {
        if (call != null)
        {
            call.cancel();
        }
    }


}
