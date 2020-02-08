package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.AreaManageContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaAdapter;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.EVENT_AREA_ADD;
import static com.anlv.prevention.assistant.app.utils.ConstantUtils.EVENT_AREA_EDIT;


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
@ActivityScope
public class AreaManagePresenter extends BasePresenter<AreaManageContract.Model, AreaManageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private AreaAdapter mAdapter;

    @Inject
    AreaManagePresenter(AreaManageContract.Model model, AreaManageContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.mAdapter = null;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message message) {
        switch (message.what) {
            case EVENT_AREA_ADD:
                if (ObjectUtils.isNotEmpty(mAdapter)) {
                    mAdapter.insertItem((Area) message.obj);
                }
                break;
            case EVENT_AREA_EDIT:
                if (ObjectUtils.isNotEmpty(mAdapter)) {
                    mAdapter.updateItem((Area) message.obj);
                }
                break;
        }
    }

    public void refresh() {
        mModel.queryArea()
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        if (ObjectUtils.isEmpty(mAdapter)) {
                            mAdapter = new AreaAdapter(result.getResult());
                            mRootView.setAdapter(mAdapter);
                        } else {
                            mAdapter.updateDataList(result.getResult());
                        }
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("管控区查询失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }

    public void areaDelete(Area area) {
        mModel.delArea(area.getId())
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        mAdapter.deleteItem(area);
                        mRootView.showMessage("管控区删除成功");
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("管控区删除失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }
}
