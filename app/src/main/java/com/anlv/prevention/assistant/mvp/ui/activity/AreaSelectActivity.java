package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.anlv.prevention.assistant.di.component.DaggerAreaSelectComponent;
import com.anlv.prevention.assistant.mvp.contract.AreaSelectContract;
import com.anlv.prevention.assistant.mvp.presenter.AreaSelectPresenter;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaSelectAdapter;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2020 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AreaSelectActivity extends BaseActivity<AreaSelectPresenter> implements AreaSelectContract.View {

    @BindView(R.id.area_select_list_rv)
    RecyclerView rvList;
    @BindColor(R.color.separator_line)
    int colorSeparatorLine;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAreaSelectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_area_select; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RecyclerViewDivider.with(this)
                .color(colorSeparatorLine)
                .build()
                .addTo(rvList);
        ArmsUtils.configRecyclerView(rvList, new LinearLayoutManager(this));
        mPresenter.getAreaList();
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

    @OnClick(R.id.area_select_back_iv)
    void onBackClicked() {
        killMyself();
    }

    @Override
    public void setAdapter(AreaSelectAdapter adapter) {
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Intent intent = new Intent();
            intent.putExtra("data", JsonUtils.writeValueAsString(data));
            setResult(RESULT_OK, intent);
            killMyself();
        });
    }
}
