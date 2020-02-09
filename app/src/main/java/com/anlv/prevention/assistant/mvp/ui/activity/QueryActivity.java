package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerQueryComponent;
import com.anlv.prevention.assistant.mvp.contract.QueryContract;
import com.anlv.prevention.assistant.mvp.presenter.QueryPresenter;
import com.anlv.prevention.assistant.mvp.ui.adapter.InfoAdapter;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
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
 * Created by MVPArmsTemplate on 02/06/2020 20:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class QueryActivity extends BaseActivity<QueryPresenter> implements QueryContract.View {

    @BindView(R.id.query_identity_et)
    EditText etIdentity;
    @BindView(R.id.query_result_list_rv)
    RecyclerView rvList;
    @BindColor(R.color.separator_line)
    int colorSeparatorLine;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQueryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_query; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RecyclerViewDivider.with(this)
                .color(colorSeparatorLine)
                .build()
                .addTo(rvList);
        ArmsUtils.configRecyclerView(rvList, new LinearLayoutManager(this));
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

    @OnClick(R.id.query_back_iv)
    void onBackClicked() {
        finish();
    }

    @OnClick(R.id.query_start_btn)
    void onStartClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        String identity = etIdentity.getText().toString().trim();
        if (ObjectUtils.isEmpty(identity)) {
            showMessage("请输入身份证号码");
            return;
        }
        if (!RegexUtils.isIDCard18(identity)) {
            showMessage("身份证号码不正确");
            return;
        }
        mPresenter.queryRecent(identity);
    }

    @Override
    public void setAdapter(InfoAdapter adapter) {
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Intent intent = new Intent();
            intent.putExtra("data", JsonUtils.writeValueAsString(data));
            setResult(RESULT_OK, intent);
            killMyself();
        });
    }
}
