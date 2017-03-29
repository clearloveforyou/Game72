package com.greenhand.game73.okhttp;


import com.greenhand.game73.okhttp.builder.GetBuilder;
import com.greenhand.game73.okhttp.builder.HeadBuilder;
import com.greenhand.game73.okhttp.builder.OtherRequestBuilder;
import com.greenhand.game73.okhttp.builder.PostFileBuilder;
import com.greenhand.game73.okhttp.builder.PostFormBuilder;
import com.greenhand.game73.okhttp.builder.PostStringBuilder;
import com.greenhand.game73.okhttp.callback.Callback;
import com.greenhand.game73.okhttp.request.RequestCall;
import com.greenhand.game73.okhttp.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by hsl on 17/3/21.
 */

/**
 * Created by clearlove on 2016/3/16.
 * OkHttp的使用：
 * 1.添加依赖：compile 'com.squareup.okhttp3:okhttp:3.2.0'
 * 2.初始化OkHttpClient；官方建议OkHttpClient在一个APP中只实例化一次。
 * 3.get请求：
 * >1.创建一个Request
 * >2.创建Call任务 ：Call call = mOkHttpClient.newCall(request)
 * >3.将请求加入调度 :
 * >1.同步:call.execute()
 * >2.异步：call.enqueue()
 * >4.取消请求：在生命周期的onstop调用call.cancel()
 */
public class OkHttpUtils
{
    public static final long DEFAULT_MILLISECONDS = 10_000L;//????求解释
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    /**
     * 此处原本应该是private,但是考虑到可以再application中进行一些初始化
     * 所以才改为public，但是仍旧可以实现本类单例，OKhttpclient单例
     * @param okHttpClient
     */
    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        return initClient(null);
    }


    public Executor getDelivery()
    {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    /**
     * get请求
     * @return
     */
    public static GetBuilder get()
    {
        return new GetBuilder();
    }

    /**
     * 提交一个字符串到服务器
     * @return
     */
    public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }

    /**
     * 将文件作为请求体，发送到服务器。
     * @return
     */
    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }

    /**
     * post请求  表单
     * @return
     */
    public static PostFormBuilder post()
    {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put()
    {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head()
    {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete()
    {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch()
    {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        //开始调用okhttp的接口
        //call.同步或异步
        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                //网络断了，失败的请求，走这个方法
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response)
            {
                //请求有返回，看看返回码
                try
                {
                    //如果请求已经撤销
                    if (call.isCanceled())
                    {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }
                    //请求没有响应，返回502等等。。。40x....
                    if (!finalCallback.validateReponse(response, id))
                    {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }
                    //对响应的正文做处理
                    Object o = finalCallback.parseNetworkResponse(response, id);
                    //请求成功，获取到数据
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally
                {
                    if (response.body() != null)
                        //关闭流吧？？？？
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id)
    {
        if (callback == null) return;////??????
        //？？？为什么要用线程处理
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id)
    {
        if (callback == null) return;
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    /**
     * 取消请求
     * @param tag
     */
    public void cancelTag(Object tag)
    {
        //取消等待执行的call
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        //取消正在执行的call
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    /**
     * 改成枚举岂不爽哉
     */
    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

