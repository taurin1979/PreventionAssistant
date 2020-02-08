package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.di.component.DaggerSplashComponent;
import com.anlv.prevention.assistant.mvp.contract.SplashContract;
import com.anlv.prevention.assistant.mvp.presenter.SplashPresenter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2020 17:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.loadData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void gotoNextPage() {
        if (ObjectUtils.isEmpty(GlobalUtils.gather)) {
            ActivityUtils.startActivity(LoginActivity.class);
        } else {
            switch (GlobalUtils.loginType) {
                case 1:
                    ActivityUtils.startActivity(ManagerActivity.class);
                    break;
                case 2:
                    ActivityUtils.startActivity(GatherActivity.class);
                    break;
                default:
                    ActivityUtils.startActivity(LoginActivity.class);
                    break;
            }
        }
        killMyself();
    }
}
