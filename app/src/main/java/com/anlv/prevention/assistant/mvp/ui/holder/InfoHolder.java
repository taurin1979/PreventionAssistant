package com.anlv.prevention.assistant.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;
import com.blankj.utilcode.util.TimeUtils;

import butterknife.BindView;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   :
 * </pre>
 */
public class InfoHolder extends BaseHolder<Info> {

    @BindView(R.id.list_item_info_name_tv)
    TextView tvName;
    @BindView(R.id.list_item_info_identity_tv)
    TextView tvIdentity;
    @BindView(R.id.list_item_info_phone_tv)
    TextView tvPhone;
    @BindView(R.id.list_item_info_area_tv)
    TextView tvArea;
    @BindView(R.id.list_item_info_temperature_tv)
    TextView tvTemperature;
    @BindView(R.id.list_item_info_date_tv)
    TextView tvDate;

    public InfoHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull Info data, int position) {
        try {
            tvName.setText(data.getName());
            tvIdentity.setText(data.getCertificateNumber());
            tvPhone.setText(data.getPhoneNumber());
            tvArea.setText(data.getAreaName());
            tvTemperature.setText(String.valueOf(data.getTemperature()));
            tvDate.setText(TimeUtils.date2String(data.getCreateTime(), "yyyy-MM-dd HH:mm"));
        } catch (Exception ignored) {
        }
    }
}
