package com.anlv.prevention.assistant.mvp.ui.adapter;

import android.view.View;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;
import com.anlv.prevention.assistant.mvp.ui.holder.BaseHolder;
import com.anlv.prevention.assistant.mvp.ui.holder.InfoHolder;

import java.util.List;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 采集信息列表适配器。
 * </pre>
 */
public class InfoAdapter extends BaseAdapter<Info> {

    public InfoAdapter(List<Info> dataList) {
        super(dataList);
    }

    @Override
    public BaseHolder<Info> getHolder(View view, int viewType) {
        return new InfoHolder(view);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_info;
    }

    @Override
    protected boolean compareData(Info data1, Info data2) {
        return data1.getId().equals(data2.getId());
    }
}
