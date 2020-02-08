package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerLoginComponent;
import com.anlv.prevention.assistant.mvp.contract.LoginContract;
import com.anlv.prevention.assistant.mvp.presenter.LoginPresenter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 14:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_gather_btn)
    RadioButton btnGather;
    @BindView(R.id.login_manager_btn)
    RadioButton btnManager;
    @BindView(R.id.login_gather_panel_ll)
    LinearLayout llGatherPanel;
    @BindView(R.id.login_manager_panel_ll)
    LinearLayout llManagerPanel;
    @BindView(R.id.login_gather_phone_et)
    EditText etGatherPhone;
    @BindView(R.id.login_gather_pin_et)
    EditText etGatherPin;
    @BindView(R.id.login_manager_phone_et)
    EditText etManagerPhone;
    @BindView(R.id.login_manager_code_et)
    EditText etManagerCode;
    @BindView(R.id.login_manager_get_code_btn)
    Button btnGetCode;

    private long exitTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        btnGather.setButtonDrawable(new StateListDrawable());
        btnManager.setButtonDrawable(new StateListDrawable());
        //TODO:测试
        etGatherPhone.setText("13512345678");
        etGatherPin.setText("888888");
        etManagerPhone.setText("13588455586");
        etManagerCode.setText("888888");
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onBackPressed() {
        exitAfterTwice();
    }

    @OnCheckedChanged(R.id.login_gather_btn)
    void onGatherChecked(boolean checked) {
        if (checked) {
            llGatherPanel.setVisibility(View.VISIBLE);
            llManagerPanel.setVisibility(View.GONE);
        } else {
            llGatherPanel.setVisibility(View.GONE);
            llManagerPanel.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.login_manager_get_code_btn)
    void onGetCodeClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        String phoneNumber = etManagerPhone.getText().toString().trim();
        if (ObjectUtils.isEmpty(phoneNumber)) {
            etManagerPhone.requestFocus();
            showMessage("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phoneNumber)) {
            etManagerPhone.requestFocus();
            showMessage("手机号码不正确");
            return;
        }
        mPresenter.getPhoneCode(phoneNumber);
    }

    @OnClick(R.id.login_ok_btn)
    void onOkClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        if (btnGather.isChecked()) {
            String phoneNumber = etGatherPhone.getText().toString().trim();
            if (ObjectUtils.isEmpty(phoneNumber)) {
                etGatherPhone.requestFocus();
                showMessage("请输入手机号码");
                return;
            }
            if (!RegexUtils.isMobileSimple(phoneNumber)) {
                etGatherPhone.requestFocus();
                showMessage("手机号码不正确");
                return;
            }

            String pinNumber = etGatherPin.getText().toString().trim();
            if (ObjectUtils.isEmpty(pinNumber)) {
                etGatherPin.requestFocus();
                showMessage("请输入PIN码");
                return;
            }
            if (pinNumber.length() != 6) {
                etGatherPin.requestFocus();
                showMessage("PIN码必须为6位数字");
                return;
            }
            mPresenter.gatherLogin(phoneNumber, pinNumber);
        } else {
            String phoneNumber = etManagerPhone.getText().toString().trim();
            if (ObjectUtils.isEmpty(phoneNumber)) {
                etManagerPhone.requestFocus();
                showMessage("请输入手机号码");
                return;
            }
            if (!RegexUtils.isMobileSimple(phoneNumber)) {
                etManagerPhone.requestFocus();
                showMessage("手机号码不正确");
                return;
            }
            String phoneCode = etManagerCode.getText().toString().trim();
            if (ObjectUtils.isEmpty(phoneCode)) {
                etManagerCode.requestFocus();
                showMessage("请输入验证码");
                return;
            }
            if (phoneCode.length() != 6) {
                etManagerCode.requestFocus();
                showMessage("验证码为6位数字");
                return;
            }
            mPresenter.managerLogin(phoneNumber, phoneCode);
        }
    }

    @Override
    public void getPhoneCodeSuccess() {
        btnGetCode.setEnabled(false);
        mPresenter.countDown();
    }

    @Override
    public void setCountDown(long count) {
        if (count == 0) {
            btnGetCode.setText(R.string.login_get_phone_code);
            btnGetCode.setEnabled(true);
        } else {
            btnGetCode.setText(String.format(Locale.getDefault(), "剩余%d秒", count));
        }
    }

    @Override
    public void loginSuccess() {
        switch (GlobalUtils.loginType) {
            case 1:
                ActivityUtils.startActivity(ManagerActivity.class);
                break;
            case 2:
                ActivityUtils.startActivity(GatherActivity.class);
                break;
        }
    }

    public void exitAfterTwice() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showMessage("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            ArmsUtils.exitApp();
        }
    }
}
