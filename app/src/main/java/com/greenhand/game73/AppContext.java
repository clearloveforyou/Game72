package com.greenhand.game73;

import android.app.Application;

/**
 * project: Game73
 * package: com.greenhand.game73
 * author: HouShengLi
 * time: 2017/3/24 01:53
 * e-mail:13967189624@163.com
 * description:
 *            1.保存长久存在的一些变量public static final int NETTYPE_WIFI = 0x01;
 *            2.获取一个上下文，在不能获取到上下文的工具类中调用
 */

public class AppContext extends Application {

    /**
     * application实例
     */
    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
