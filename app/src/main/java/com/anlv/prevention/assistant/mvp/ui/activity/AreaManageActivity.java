package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerAreaManageComponent;
import com.anlv.prevention.assistant.mvp.contract.AreaManageContract;
import com.anlv.prevention.assistant.mvp.presenter.AreaManagePresenter;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.LIST_OPERATION_DEL;
import static com.anlv.prevention.assistant.app.utils.ConstantUtils.LIST_OPERATION_EDIT;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AreaManageActivity extends BaseActivity<AreaManagePresenter> implements AreaManageContract.View {

    @BindView(R.id.area_manage_list_rv)
    RecyclerView rvList;
    @BindColor(R.color.separator_line)
    int colorSeparatorLine;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAreaManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_area_manage; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RecyclerViewDivider.with(this)
                .color(colorSeparatorLine)
                .build()
                .addTo(rvList);
        ArmsUtils.configRecyclerView(rvList, new LinearLayoutManager(this));
        mPresenter.refresh();
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

    @OnClick(R.id.area_manage_back_iv)
    void onBackClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        killMyself();
    }

    @OnClick(R.id.area_manage_add_tv)
    void onAddClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        ActivityUtils.startActivity(AreaAddActivity.class);
    }

    @Override
    public void setAdapter(AreaAdapter adapter) {
        rvList.setAdapter(adapter);
        adapter.setOnOperationListener((operation, data, position) -> {
            switch (operation) {
                case LIST_OPERATION_EDIT:
                    Bundle bundle = new Bundle();
                    bundle.putString("data", JsonUtils.writeValueAsString(data));
                    ActivityUtils.startActivity(bundle, AreaEditActivity.class);
                    break;
                case LIST_OPERATION_DEL:
                    mPresenter.areaDelete(data);
                    break;
            }
        });
    }
}
