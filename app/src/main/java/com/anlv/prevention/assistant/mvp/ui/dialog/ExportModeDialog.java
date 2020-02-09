package com.anlv.prevention.assistant.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.anlv.prevention.assistant.R;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-09
 *     desc   : 导出方式选择
 * </pre>
 */
public class ExportModeDialog extends Dialog {

    @BindView(R.id.export_mode_mail_address_et)
    EditText etMailAddress;

    private OnExportModeListener onExportModeListener;

    private ExportModeDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        View view = View.inflate(context, R.layout.dialog_export_mode, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        String email = SPUtils.getInstance().getString("email", null);
        if (ObjectUtils.isNotEmpty(email))
            etMailAddress.setText(email);
    }

    private void setOnExportModeListener(OnExportModeListener listener) {
        onExportModeListener = listener;
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mPositive;
        private String mNegative;
        private OnExportModeListener mListener;

        public Builder(Context context) {
            mContext = context;
        }

        public ExportModeDialog.Builder setOnExportModeListener(OnExportModeListener listener) {
            mListener = listener;
            return this;
        }

        public ExportModeDialog build() {
            ExportModeDialog dialog = new ExportModeDialog(mContext);
            dialog.setOnExportModeListener(mListener);
            dialog.setCancelable(false);
            return dialog;
        }
    }

    @OnClick(R.id.export_mode_close_iv)
    void onCloseClicked() {
        dismiss();
    }

    @OnClick(R.id.export_mode_mail_btn)
    void onMailClicked() {
        String mailAddress = etMailAddress.getText().toString().trim();
        if (ObjectUtils.isEmpty(mailAddress)) {
            ArmsUtils.snackbarText("请输入邮箱地址");
            return;
        }
        if (!RegexUtils.isEmail(mailAddress)) {
            ArmsUtils.snackbarText("邮箱地址格式不正确");
            return;
        }
        SPUtils.getInstance().put("email", mailAddress);
        if (ObjectUtils.isNotEmpty(onExportModeListener))
            onExportModeListener.onMode(1, mailAddress);
        dismiss();
    }

    public interface OnExportModeListener {
        void onMode(int mode, String data);
    }
}
