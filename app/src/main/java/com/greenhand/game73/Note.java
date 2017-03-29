package com.greenhand.game73;

import android.graphics.Bitmap;
import android.os.Environment;

import com.greenhand.game73.okhttp.OkHttpUtils;
import com.greenhand.game73.okhttp.callback.BitmapCallback;
import com.greenhand.game73.okhttp.callback.FileCallBack;
import com.greenhand.game73.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * project: Game73
 * package: com.greenhand.game73
 * author: HouShengLi
 * time: 2017/3/20 23:58
 * e-mail:13967189624@163.com
 * description:
 */

public class Note {

    private void test(){

        get();
    }

    private void test2(){
        get2();
    }

    private void test3(){
        postt();
    }

    private void test4(){

        postt2();
    }
    private void test5(){

    }
    private void test6(){

    }
    private void test7(){

    }
    private void test8(){

    }





















    private void get(){
        //1.
        OkHttpClient o = new OkHttpClient();
        //2.
        Request.Builder builder = new Request.Builder();
        builder.url("")
                .headers(null)
                .addHeader("","")
                .tag("");
        Request request = builder.build();
        //3.

        //
        OkHttpClient.Builder builder1 = o.newBuilder();
        builder1.readTimeout(10, TimeUnit.MILLISECONDS);//danwei
        Call call = o.newCall(request);
        //4.异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //工作线程
            }
        });
    }

    private void get2(){

        String url = "http://www.csdn.net/";
        OkHttpUtils
                .get()
                .url(url)//可以使完整的url,也可以是少路径的url
                .tag("")//???用于取消请求
                .id(0)//在callback中用于区分不同的请求
                .headers(null)//添加多个请求头
                .addHeader("","")//添加单个请求头
                .addParams("username", "hyman")//get的参数
                .addParams("password", "123")
                .build()
                .writeTimeOut(10)
                .readTimeOut(10)
                .connTimeOut(10)//如果你想单独的给这次请求设置读，写，连接超时，请这样做，，否则就是application中的配置，如果application中没配置就是okhttp中的默认配置
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //请求失败：：：：断网等。。。。。
                        //如果请求已经撤销，也走这里
                        //若果请求返回码，不在[200,300)，既没有数据，也走这里 //同事抛出异常"request failed , reponse's code is : "

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //或得的响应正文
                    }
                });

    }

    private void postt(){

        //只要用于表单式提交
        OkHttpClient o = new OkHttpClient().newBuilder().readTimeout(10,TimeUnit.MILLISECONDS).build();

        //创建请求内容FormBody：参数
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("","");//添加参数
        FormBody formBody = builder.build();

        //
        Request.Builder b = new Request.Builder();
        Request request = b.url("")
                .tag(null)
                .headers(null)
                .header("","")//覆盖
                .addHeader("", "")//追加
                .post(formBody)
                .build();

        //
        Call call = o.newCall(request);
        //
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void postt2(){


        OkHttpUtils
                .post()
                .url("")
                .tag(null)
                .id(0)
                .addHeader("","")//添加头部
                .headers(null)//直接给个头部的map
                .params(null)//直接给个参数map
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .readTimeOut(10)//设置超时
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {

                        //加进度条就可以显示进度了
                    }
                });
    }

    /**
     * post的代码也比较简单，相信大家肯定能都看懂。但是在这里面涉及到一个比较重要的类MediaType，该类来指定post上传的参数类型。
     FormBody涉及常见的有三种：
     application/x-www-form-urlencoded 数据是个普通表单
     multipart/form-data 数据里有文件
     application/json 数据是个json
     但是好像以上的普通表单并没有指定MediaType，这是因为FormBody继承了RequestBody，它已经指定了数据类型为application/x-www-form-urlencoded。
     */
    private void postJson(){

        OkHttpClient okHttpClient = new OkHttpClient();
        Map<String,String> map = new HashMap<>();
        map.put("","");
//        //这里用的是fastjson进行解析的
//        String jsonstr= JSON.toJSONString(map);
        String jsonStr = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        Request request = new Request.Builder()
                .url("")
                .header("", "")
                .addHeader("", "")
                .headers(null)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void postJson2(){

        OkHttpUtils
                .postString()
                .url("")
                .content("")//上传的json字符串
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    private void postFile(){

        OkHttpClient okHttpClient = new OkHttpClient();
        final MediaType MEDIA_TYPE_TEXT = MediaType.parse("image/png");
        File file = new File("lujin");
        //RequsetBody的子类
        //如果接口没有file字段，直接上传文件，可以这样写：.post(RequestBody.create(MEDIA_TYPE_TEXT, file))
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//这个有五种类型，不是很明白
                .addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_TEXT, file)).build();
        Request request = new Request.Builder()
                .url("")
                .post(multipartBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 单文件上传
     */
    private void postFile2(){

        OkHttpUtils
                .postFile()
                .url("")
                .file(null)//单文件上传
                .build()
                .execute(new FileCallBack("","kkkk") {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {

                    }
                });

    }

    /**
     * 表单形式上传文件
     */
    private void postFile3(){
        /**
         * 支持单个多个文件，addFile的第一个参数为文件的key，
         * 即类别表单中<input type="file" name="mFile"/>的name属性
         */
        File file = new File("hhhh");
        File file2 = new File("ssss");

        OkHttpUtils.post()
                .files("",null)
                .addFile("mFile", "messenger_01.png", file)
                .addFile("mFile", "test1.txt", file2)
                .url("")
                .params(null)
                .addParams("","")
                .headers(null)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        //设置上传进度
                    }
                });

    }

    private void downLoad(){
        //文件下载，为get,所以很简单
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = "";
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                //这个ResponseBody的byteStream()可以获得一个输入流是，我们就应该想到
                //他可以下载大文件
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/wy.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {

                }

            }
        });
    }

    private void downLoad2(){

        OkHttpUtils//
                .get()//
                .url("")//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        //设置进度条 progress:比率
                    }
                });

    }

    private void imageLoader(){

        OkHttpUtils
                .get()//
                .url("")//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {

                        //直接img.setBitMap(response);
                    }
                });
    }

}
