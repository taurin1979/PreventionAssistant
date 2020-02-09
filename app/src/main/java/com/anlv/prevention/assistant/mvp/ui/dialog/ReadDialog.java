package com.anlv.prevention.assistant.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anlv.prevention.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-09
 *     desc   :
 * </pre>
 */
public class ReadDialog extends Dialog {

    @BindView(R.id.dialog_read_title_tv)
    TextView tvTitle;
    @BindView(R.id.dialog_read_content_tv)
    TextView tvContent;

    private ReadDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        View view = View.inflate(context, R.layout.dialog_read, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mContent;

        public Builder(Context context) {
            mContext = context;
        }

        public ReadDialog.Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public ReadDialog.Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public ReadDialog build() {
            ReadDialog dialog = new ReadDialog(mContext);
            dialog.tvTitle.setText(mTitle);
            dialog.tvContent.setText(mContent);
            dialog.setCancelable(false);
            return dialog;
        }
    }

    @OnClick(R.id.dialog_read_close_iv)
    void onCloseClicked() {
        dismiss();
    }
}
