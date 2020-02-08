package com.anlv.prevention.assistant.mvp.ui.listener;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2018/5/29
 *     desc   : 列表控件成员事件监听。
 * </pre>
 */
public interface OnOperationListener<T> {
    void onOperation(int operation, @NonNull T data, int position);
}
