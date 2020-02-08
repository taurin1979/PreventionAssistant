package com.anlv.prevention.assistant.app.utils;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-06
 *     desc   :
 * </pre>
 */
public interface ConstantUtils {
    String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";//默认的日期时间格式
    int MAX_COUNT_TIME = 60;//倒计时时间设置

    int REQUEST_GATHER_SELECT = 0x101;//历史采集数据选择
    int REQUEST_AREA_SELECT = 0x102;//管控区选择

    int EVENT_AREA_ADD = 0x200;//管控区添加
    int EVENT_AREA_EDIT = 0x201;//管控区编辑
    int EVENT_GATHER_ADD = 0x210;//采集员添加
    int EVENT_GATHER_EDIT = 0x211;//采集员编辑

    int LIST_OPERATION_EDIT = 0x10;//点击编辑按钮
    int LIST_OPERATION_DEL = 0x20;//点击删除按钮
}
