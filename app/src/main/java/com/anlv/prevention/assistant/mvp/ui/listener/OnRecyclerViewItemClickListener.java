package com.anlv.prevention.assistant.mvp.ui.listener;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2018/5/29
 *     desc   : Item点击事件监听。
 * </pre>
 */
public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(@NonNull View view, int viewType, @NonNull T data, int position);
}
