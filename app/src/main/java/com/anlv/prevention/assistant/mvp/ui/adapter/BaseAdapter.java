package com.anlv.prevention.assistant.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.mvp.ui.holder.BaseHolder;
import com.anlv.prevention.assistant.mvp.ui.listener.OnOperationListener;
import com.anlv.prevention.assistant.mvp.ui.listener.OnRecyclerViewItemClickListener;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   :
 * </pre>
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private OnRecyclerViewItemClickListener<T> mOnItemClickListener;
    private OnOperationListener<T> mOnOperationListener;
    protected List<T> mDataList;

    public BaseAdapter(List<T> dataList) {
        super();
        updateDataList(dataList);
    }

    @NonNull
    @Override
    public BaseHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        BaseHolder<T> mHolder = getHolder(rootView, viewType);
        mHolder.setOnItemClickListener((view, position) -> {
            if (ObjectUtils.isNotEmpty(mOnItemClickListener) && ObjectUtils.isNotEmpty(mDataList)) {
                mOnItemClickListener.onItemClick(view, viewType, mDataList.get(position), position);
            }
        });
        if (ObjectUtils.isNotEmpty(mOnOperationListener))
            mHolder.setOnOperationListener(mOnOperationListener);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        holder.setData(mDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (ObjectUtils.isEmpty(mDataList))
            return 0;
        return mDataList.size();
    }

    public void updateDataList(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 获得列表数据
     */
    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 获得item的数据
     */
    public T getItem(int position) {
        return ObjectUtils.isEmpty(mDataList) ? null : mDataList.get(position);
    }

    /**
     * 插入Item数据
     */
    public void insertItem(T data) {
        mDataList.add(0, data);
        notifyItemInserted(0);
    }

    /**
     * 更新Item数据
     */
    public void updateItem(T data) {
        if (ObjectUtils.isEmpty(mDataList))
            return;
        for (int i = 0; i < mDataList.size(); i++) {
            T item = mDataList.get(i);
            if (compareData(item, data)) {
                mDataList.set(i, data);
                notifyItemChanged(i);
                return;
            }
        }
    }

    /**
     * 删除item数据
     */
    public void deleteItem(T data) {
        int index = mDataList.indexOf(data);
        if (index >= 0) {
            mDataList.remove(data);
            notifyItemRemoved(index);
        }
    }

    /**
     * 子类实现提供holder
     */
    public abstract BaseHolder<T> getHolder(View view, int viewType);

    /**
     * 提供Item的布局
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 关键字比较，用于判断两个对象是否相等
     */
    protected abstract boolean compareData(T data1, T data2);

    /**
     * 遍历所有hodler,释放他们需要释放的资源
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof BaseHolder) {
                ((BaseHolder) viewHolder).onRelease();
            }
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnOperationListener(OnOperationListener<T> listener) {
        this.mOnOperationListener = listener;
    }
}
