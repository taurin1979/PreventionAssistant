package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.manager.EventManager;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.AreaAddContract;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.EVENT_AREA_ADD;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AreaAddPresenter extends BasePresenter<AreaAddContract.Model, AreaAddContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    AreaAddPresenter(AreaAddContract.Model model, AreaAddContract.View rootView) {
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

    public void addArea(String name, String pin) {
        mModel.addArea(name, pin)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        EventManager.getInstance().sendMessage(EVENT_AREA_ADD, result.getResult());
                        mRootView.showMessage("管控区添加成功");
                        mRootView.killMyself();
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("管控区添加失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }
}
