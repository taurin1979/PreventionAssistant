package com.anlv.prevention.assistant.mvp.ui.listener;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2018/5/29
 *     desc   : Item长按事件监听。
 * </pre>
 */
public interface OnViewLongClickListener {
    void onViewLongClick(@NonNull View view, int position);
}
