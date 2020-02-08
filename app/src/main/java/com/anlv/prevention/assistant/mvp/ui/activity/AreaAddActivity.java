package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerAreaAddComponent;
import com.anlv.prevention.assistant.mvp.contract.AreaAddContract;
import com.anlv.prevention.assistant.mvp.presenter.AreaAddPresenter;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AreaAddActivity extends BaseActivity<AreaAddPresenter> implements AreaAddContract.View {

    @BindView(R.id.area_add_name_et)
    EditText etName;
    @BindView(R.id.area_add_pin_et)
    EditText etPin;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAreaAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_area_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

    @OnClick(R.id.area_add_back_iv)
    void onBackClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        killMyself();
    }

    @OnClick(R.id.area_add_ok_btn)
    void onOkClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        String name = etName.getText().toString().trim();
        if (ObjectUtils.isEmpty(name)) {
            showMessage("请输入管控区名称");
            return;
        }
        String pin = etPin.getText().toString().trim();
        if (ObjectUtils.isEmpty(pin)) {
            showMessage("请输入管控区PIN码");
            return;
        }
        if (pin.length() != 6) {
            showMessage("PIN码必须为6位数字");
            return;
        }
        mPresenter.addArea(name, pin);
    }
}
