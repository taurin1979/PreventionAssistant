package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerGatherAddComponent;
import com.anlv.prevention.assistant.mvp.contract.GatherAddContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.presenter.GatherAddPresenter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.REQUEST_AREA_SELECT;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GatherAddActivity extends BaseActivity<GatherAddPresenter> implements GatherAddContract.View {

    @BindView(R.id.gather_add_name_et)
    EditText etName;
    @BindView(R.id.gather_add_phone_et)
    EditText etPhone;
    @BindView(R.id.gather_add_area_tv)
    TextView tvArea;
    @BindColor(R.color.font_normal)
    int colorFontNormal;

    private String areaId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGatherAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_gather_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == REQUEST_AREA_SELECT && resultCode == RESULT_OK) {
            String data = intent.getStringExtra("data");
            if (ObjectUtils.isNotEmpty(data)) {
                Area area = JsonUtils.readValue(data, Area.class);
                if (ObjectUtils.isNotEmpty(area)) {
                    areaId = area.getId();
                    tvArea.setText(area.getAreaName());
                    tvArea.setTextColor(colorFontNormal);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @OnClick(R.id.gather_add_back_iv)
    void onBackClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        killMyself();
    }

    @OnClick(R.id.gather_add_area_tv)
    void onAreaClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        ActivityUtils.startActivityForResult(this, AreaSelectActivity.class, REQUEST_AREA_SELECT);
    }

    @OnClick(R.id.gather_add_ok_btn)
    void onOkClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        String name = etName.getText().toString().trim();
        if (ObjectUtils.isEmpty(name)) {
            showMessage("请输入采集员名称");
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (ObjectUtils.isEmpty(phone)) {
            showMessage("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            showMessage("手机号码不正确");
            return;
        }

        if (ObjectUtils.isEmpty(areaId)) {
            showMessage("请选择管控区");
            return;
        }

        mPresenter.gatherAdd(areaId, name, phone);
    }
}
