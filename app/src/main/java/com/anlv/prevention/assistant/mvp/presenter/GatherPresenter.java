package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.GatherContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
@ActivityScope
public class GatherPresenter extends BasePresenter<GatherContract.Model, GatherContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    GatherPresenter(GatherContract.Model model, GatherContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void report(String identity, String name, String phone, String address, float temperature, String remark) {
        mModel.report(identity, name, phone, address, String.valueOf(temperature), remark)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<String> result) {
                        if (result.isSucc()) {
                            mRootView.showMessage("数据上报成功");
                            mRootView.reportSuccess();
                        } else {
                            if (ObjectUtils.isEmpty(result.getMessage())) {
                                mRootView.showMessage("数据上报失败");
                            } else {
                                mRootView.showMessage(result.getMessage());
                            }
                        }
                    }
                });
    }
}
