package com.anlv.prevention.assistant.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;

import butterknife.BindView;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   :
 * </pre>
 */
public class AreaSelectHolder extends BaseHolder<Area> {

    @BindView(R.id.list_item_area_select_name_tv)
    TextView tvName;

    public AreaSelectHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull Area data, int position) {
        tvName.setText(data.getAreaName());
    }
}
