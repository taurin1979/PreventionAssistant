package com.anlv.prevention.assistant.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anlv.prevention.assistant.R;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.di.component.DaggerExportComponent;
import com.anlv.prevention.assistant.mvp.contract.ExportContract;
import com.anlv.prevention.assistant.mvp.presenter.ExportPresenter;
import com.anlv.prevention.assistant.mvp.ui.adapter.InfoAdapter;
import com.anlv.prevention.assistant.mvp.ui.dialog.ExportModeDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ExportActivity extends BaseActivity<ExportPresenter> implements ExportContract.View {

    private final static String TIME_PATTERN = "yyyy-MM-dd";

    @Inject
    RxPermissions mRxPermissions;
    @BindView(R.id.export_begin_time_et)
    EditText etBeginTime;
    @BindView(R.id.export_end_time_et)
    EditText etEndTime;
    @BindView(R.id.export_list_rv)
    RecyclerView rvList;
    @BindColor(R.color.separator_line)
    int colorSeparatorLine;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExportComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_export; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        etBeginTime.setInputType(InputType.TYPE_NULL);
        etEndTime.setInputType(InputType.TYPE_NULL);
        RecyclerViewDivider.with(this)
                .color(colorSeparatorLine)
                .build()
                .addTo(rvList);
        ArmsUtils.configRecyclerView(rvList, new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mRxPermissions = null;
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

    @OnClick(R.id.export_back_iv)
    void onBackClicked() {
        killMyself();
    }

    @OnClick(R.id.export_begin_time_et)
    void onStartTimeClicked() {
        TimePickerView pickerView = new TimePickerBuilder(this, (date, v) -> etBeginTime.setText(TimeUtils.date2String(date, TIME_PATTERN)))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleText("起始时间")//标题文字
                .build();
        String startTime = etBeginTime.getText().toString().trim();
        if (ObjectUtils.isNotEmpty(startTime)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(TimeUtils.string2Date(startTime, TIME_PATTERN));
            pickerView.setDate(calendar);
        }
        pickerView.show();
    }

    @OnClick(R.id.export_end_time_et)
    void onEndTimeClicked() {
        TimePickerView pickerView = new TimePickerBuilder(this, (date, v) -> etEndTime.setText(TimeUtils.date2String(date, TIME_PATTERN)))
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleText("结束时间")//标题文字
                .build();
        String endTime = etEndTime.getText().toString().trim();
        if (ObjectUtils.isNotEmpty(endTime)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(TimeUtils.string2Date(endTime, TIME_PATTERN));
            pickerView.setDate(calendar);
        }
        pickerView.show();
    }

    @OnClick(R.id.export_query_btn)
    void onQueryClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        String beginTime = etBeginTime.getText().toString().trim();
        if (ObjectUtils.isEmpty(beginTime)) {
            showMessage("请选择起始时间");
            return;
        }
        String endTime = etEndTime.getText().toString().trim();
        if (ObjectUtils.isEmpty(endTime)) {
            showMessage("请选择结束时间");
            return;
        }
        mPresenter.query(TimeUtils.string2Date(beginTime, TIME_PATTERN),
                TimeUtils.string2Date(endTime, TIME_PATTERN));
    }

    @OnClick(R.id.export_export_tv)
    void onExportClicked(View view) {
        if (ToolUtils.isFastDoubleClick(view))
            return;
        if (mPresenter.getExportCount() == 0) {
            showMessage("没有需要导出的数据");
            return;
        }
        new ExportModeDialog.Builder(this)
                .setOnExportModeListener((mode, data) -> {
                    if (mode == 1 && ObjectUtils.isNotEmpty(data))
                        mPresenter.exportData(data);
                }).build().show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void setAdapter(InfoAdapter adapter) {
        rvList.setAdapter(adapter);
    }
}
