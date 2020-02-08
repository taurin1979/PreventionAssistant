package com.anlv.prevention.assistant.mvp.ui.adapter;

import android.view.View;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.ui.holder.BaseHolder;
import com.anlv.prevention.assistant.mvp.ui.holder.GatherHolder;

import java.util.List;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 采集员列表适配器
 * </pre>
 */
public class GatherAdapter extends BaseAdapter<Gather> {

    public GatherAdapter(List<Gather> dataList) {
        super(dataList);
    }

    @Override
    public BaseHolder<Gather> getHolder(View view, int viewType) {
        return new GatherHolder(view);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_gather;
    }

    @Override
    protected boolean compareData(Gather data1, Gather data2) {
        return data1.getId().equals(data2.getId());
    }
}
