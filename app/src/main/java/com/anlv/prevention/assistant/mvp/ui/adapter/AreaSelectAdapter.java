package com.anlv.prevention.assistant.mvp.ui.adapter;

import android.view.View;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.ui.holder.AreaSelectHolder;
import com.anlv.prevention.assistant.mvp.ui.holder.BaseHolder;

import java.util.List;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 管控区选择列表适配器
 * </pre>
 */
public class AreaSelectAdapter extends BaseAdapter<Area> {

    public AreaSelectAdapter(List<Area> dataList) {
        super(dataList);
    }

    @Override
    public BaseHolder<Area> getHolder(View view, int viewType) {
        return new AreaSelectHolder(view);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_area_select;
    }

    @Override
    protected boolean compareData(Area data1, Area data2) {
        return data1.getId().equals(data2.getId());
    }
}
