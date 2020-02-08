package com.anlv.prevention.assistant.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.blankj.utilcode.util.ObjectUtils;

import butterknife.BindView;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.LIST_OPERATION_DEL;
import static com.anlv.prevention.assistant.app.utils.ConstantUtils.LIST_OPERATION_EDIT;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   :
 * </pre>
 */
public class GatherHolder extends BaseHolder<Gather> {

    @BindView(R.id.list_item_gather_name_tv)
    TextView tvName;
    @BindView(R.id.list_item_gather_phone_tv)
    TextView tvPhone;
    @BindView(R.id.list_item_gather_edit_iv)
    ImageView ivEdit;
    @BindView(R.id.list_item_gather_del_iv)
    ImageView ivDel;

    public GatherHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull Gather data, int position) {
        tvName.setText(data.getName());
        tvPhone.setText(data.getPhoneNumber());
        ivEdit.setOnClickListener(v -> {
            if (ObjectUtils.isNotEmpty(mOnOperationListener)) {
                mOnOperationListener.onOperation(LIST_OPERATION_EDIT, data, position);
            }
        });
        ivDel.setOnClickListener(v -> {
            if (ObjectUtils.isNotEmpty(mOnOperationListener)) {
                mOnOperationListener.onOperation(LIST_OPERATION_DEL, data, position);
            }
        });
    }
}
