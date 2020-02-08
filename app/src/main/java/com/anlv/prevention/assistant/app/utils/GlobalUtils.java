package com.anlv.prevention.assistant.app.utils;

import android.content.Context;

import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-08
 *     desc   : 全局变量
 * </pre>
 */
public class GlobalUtils {
    public static String sessionId;//服务器sessionId
    public static int loginType;//当前登录用户类型 1.管理员,2.采集员
    public static Gather gather;//当前用户对象
}
