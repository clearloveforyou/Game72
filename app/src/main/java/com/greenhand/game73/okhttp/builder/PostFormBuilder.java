package com.greenhand.game73.okhttp.builder;


import com.greenhand.game73.okhttp.request.PostFormRequest;
import com.greenhand.game73.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable
{
    private List<FileInput> files = new ArrayList<>();

    @Override
    public RequestCall build()
    {
        return new PostFormRequest(url, tag, params, headers, files,id).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files)
    {
        for (String filename : files.keySet())
        {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    /**
     * 添加文件
     * @param name 即类别表单中<input type="file" name="mFile"/>的name属性
     * @param filename 文件名字
     * @param file 文件：表单中的名字（其实就是上传的文件）
     * @return
     */
    public PostFormBuilder addFile(String name, String filename, File file)
    {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public static class FileInput
    {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file)
        {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString()
        {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }


    /**
     * 方法重写：两同、两小、一大
     * @param params
     * @return
     */
    @Override
    public PostFormBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }




}
