package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.di.component.DaggerManagerComponent;
import com.anlv.prevention.assistant.mvp.contract.ManagerContract;
import com.anlv.prevention.assistant.mvp.presenter.ManagerPresenter;
import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 18:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ManagerActivity extends BaseActivity<ManagerPresenter> implements ManagerContract.View {

    private long exitTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_manager; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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

    @Override
    public void onBackPressed() {
        exitAfterTwice();
    }

    @OnClick({R.id.manager_config_iv, R.id.manager_area_ll, R.id.manager_gather_ll, R.id.manager_export_ll})
    void onClicked(View view) {
        switch (view.getId()) {
            case R.id.manager_config_iv:
                ActivityUtils.startActivity(ConfigActivity.class);
                break;
            case R.id.manager_area_ll:
                ActivityUtils.startActivity(AreaManageActivity.class);
                break;
            case R.id.manager_gather_ll:
                ActivityUtils.startActivity(GatherManageActivity.class);
                break;
            case R.id.manager_export_ll:
                ActivityUtils.startActivity(ExportActivity.class);
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
