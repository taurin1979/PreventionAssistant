package com.anlv.prevention.assistant.mvp.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.mvp.ui.adapter.BaseAdapter;
import com.anlv.prevention.assistant.mvp.ui.listener.OnOperationListener;
import com.anlv.prevention.assistant.mvp.ui.listener.OnViewClickListener;
import com.anlv.prevention.assistant.mvp.ui.listener.OnViewLongClickListener;
import com.jess.arms.utils.ThirdViewUtil;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   :
 * </pre>
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected OnViewClickListener mOnViewClickListener = null;
    protected OnViewLongClickListener mOnViewLongClickListener = null;
    protected OnOperationListener<T> mOnOperationListener = null;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        //点击事件
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, itemView);
    }

    /**
     * 设置数据
     *
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    public abstract void setData(@NonNull T data, int position);

    /**
     * 在 Activity 的 onDestroy 中使用 {@link BaseAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    public void onRelease() {
    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnViewLongClickListener != null) {
            mOnViewLongClickListener.onViewLongClick(view, this.getAdapterPosition());
        }
        return false;
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }

    public void setOnItemLongClickListener(OnViewLongClickListener listener) {
        this.mOnViewLongClickListener = listener;
    }

    public void setOnOperationListener(OnOperationListener<T> listener) {
        this.mOnOperationListener = listener;
    }
}
