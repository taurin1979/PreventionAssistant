package com.anlv.prevention.assistant.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerGatherComponent;
import com.anlv.prevention.assistant.mvp.contract.GatherContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;
import com.anlv.prevention.assistant.mvp.presenter.GatherPresenter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.REQUEST_GATHER_SELECT;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GatherActivity extends BaseActivity<GatherPresenter> implements GatherContract.View {

    @BindView(R.id.gather_area_name_tv)
    TextView tvAreaName;
    @BindView(R.id.gather_identity_et)
    EditText etIdentity;
    @BindView(R.id.gather_name_et)
    EditText etName;
    @BindView(R.id.gather_phone_et)
    EditText etPhone;
    @BindView(R.id.gather_address_et)
    EditText etAddress;
    @BindView(R.id.gather_temperature_et)
    EditText etTemperature;
    @BindView(R.id.gather_remark_et)
    EditText etRemark;

    private long exitTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGatherComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_gather; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (ObjectUtils.isNotEmpty(GlobalUtils.gather))
            tvAreaName.setText(GlobalUtils.gather.getAreaName());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == REQUEST_GATHER_SELECT && resultCode == RESULT_OK) {
            String data = intent.getStringExtra("data");
            if (ObjectUtils.isNotEmpty(data)) {
                Info info = JsonUtils.readValue(data, Info.class);
                if (ObjectUtils.isNotEmpty(info)) {
                    etIdentity.setText(info.getCertificateNumber());
                    etName.setText(info.getName());
                    etPhone.setText(info.getPhoneNumber());
                    etAddress.setText(info.getAddress());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @OnClick(R.id.gather_config_iv)
    void onConfigClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        ActivityUtils.startActivity(ConfigActivity.class);
    }

    @OnClick(R.id.gather_query_btn)
    void onQueryClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        Bundle bundle = new Bundle();
        String identity = etIdentity.getText().toString().trim();
        bundle.putString("identity", identity);
        ActivityUtils.startActivityForResult(bundle, this, QueryActivity.class, REQUEST_GATHER_SELECT);
    }

    @OnClick(R.id.gather_submit_btn)
    void onSubmitClicked(View view) {
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

        String name = etName.getText().toString().trim();
        if (ObjectUtils.isEmpty(name)) {
            showMessage("请输入姓名");
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

        String address = etAddress.getText().toString().trim();
        if (ObjectUtils.isEmpty(address)) {
            showMessage("请输入住址");
            return;
        }

        String remark = etRemark.getText().toString().trim();

        String strTemperature = etTemperature.getText().toString().trim();
        if (ObjectUtils.isEmpty(strTemperature)) {
            showMessage("请输入体温");
            return;
        }
        try {
            float temperature = Float.valueOf(strTemperature);
            if (temperature < 30 || temperature > 60) {
                showMessage("体温输入不正确");
                return;
            }
            mPresenter.report(identity, name, phone, address, temperature, remark);
        } catch (Exception ignored) {
            showMessage("体温输入不正确");
        }
    }

    @Override
    public void reportSuccess() {
        clearData();
    }

    private void clearData() {
        etIdentity.setText(null);
        etName.setText(null);
        etPhone.setText(null);
        etAddress.setText(null);
        etTemperature.setText(null);
        etRemark.setText(null);
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
