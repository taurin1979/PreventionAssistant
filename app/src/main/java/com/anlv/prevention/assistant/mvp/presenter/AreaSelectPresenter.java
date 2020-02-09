package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.AreaSelectContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaSelectAdapter;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
@ActivityScope
public class AreaSelectPresenter extends BasePresenter<AreaSelectContract.Model, AreaSelectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private AreaSelectAdapter mAdapter;

    @Inject
    AreaSelectPresenter(AreaSelectContract.Model model, AreaSelectContract.View rootView) {
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

    public void getAreaList() {
        mModel.getAreaList()
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<List<Area>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<List<Area>> result) {
                        if (result.isSucc()) {
                            if (ObjectUtils.isEmpty(mAdapter)) {
                                mAdapter = new AreaSelectAdapter(result.getResult());
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
                    }
                });
    }
}
