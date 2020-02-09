package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.manager.EventManager;
import com.anlv.prevention.assistant.app.utils.ConstantUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.GatherEditContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
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
 * Created by MVPArmsTemplate on 02/07/2020 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GatherEditPresenter extends BasePresenter<GatherEditContract.Model, GatherEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    GatherEditPresenter(GatherEditContract.Model model, GatherEditContract.View rootView) {
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

    public void updateGather(String id, String areaId, String name, String phone) {
        mModel.updateGather(id, areaId, name, phone, null)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<Gather>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<Gather> result) {
                        if (result.isSucc()) {
                            EventManager.getInstance().sendMessage(ConstantUtils.EVENT_GATHER_EDIT, result.getResult());
                            mRootView.showMessage("采集员编辑成功");
                            mRootView.killMyself();
                        } else {
                            if (ObjectUtils.isEmpty(result.getMessage())) {
                                mRootView.showMessage("采集员编辑失败");
                            } else {
                                mRootView.showMessage(result.getMessage());
                            }
                        }
                    }
                });
    }
}
