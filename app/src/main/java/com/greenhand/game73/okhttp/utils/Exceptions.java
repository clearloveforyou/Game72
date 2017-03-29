package com.greenhand.game73.okhttp.utils;

/**
 * Created by zhy on 15/12/14.
 * 传入不合法的参数时抛出的异常
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
