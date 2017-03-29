package com.greenhand.game73.okhttp.callback;


import com.greenhand.game73.okhttp.OkHttpUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/15.
 */
public abstract class FileCallBack extends Callback<File>
{
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    public FileCallBack(String destFileDir, String destFileName)
    {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception
    {
        return saveFile(response,id);
    }


    public File saveFile(Response response,final int id) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        BufferedOutputStream bfos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            bfos = new BufferedOutputStream(new FileOutputStream(file));
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                bfos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        inProgress(finalSum * 1.0f / total,total,id);
                    }
                });
            }
            bfos.flush();

            return file;

        } finally
        {
            try
            {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (bfos != null) bfos.close();
            } catch (IOException e)
            {
            }

        }
    }


}
