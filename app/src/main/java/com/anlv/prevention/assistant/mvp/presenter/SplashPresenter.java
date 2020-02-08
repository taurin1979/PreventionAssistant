package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.mvp.contract.SplashContract;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2020 17:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private long startTime;
    private boolean isFinished = true;

    @Inject
    SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
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

    /**
     * 加载数据
     */
    public void loadData() {
        startWaiting();
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            GlobalUtils.sessionId = SPUtils.getInstance().getString("sessionId", null);
            GlobalUtils.loginType = SPUtils.getInstance().getInt("loginType", 0);
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (ObjectUtils.isNotEmpty(GlobalUtils.sessionId)) {
                        userInfo();
                    }
                }, Timber::e);
    }

    private void startWaiting() {
        startTime = TimeUtils.getNowMills();
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(value -> isFinished && (TimeUtils.getNowMills() - startTime) > 3000)
                .subscribe(result -> {
                    if (isFinished && (TimeUtils.getNowMills() - startTime) > 3000)
                        mRootView.gotoNextPage();
                }, Timber::e);
    }

    /**
     * 用户信息获取
     */
    private void userInfo() {
        mModel.gatherInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> isFinished = false)
                .doFinally(() -> isFinished = true)
                .subscribe(result -> {
                    if (result.isSucc()) {
                        GlobalUtils.gather = result.getResult();
                    }
                }, Timber::e);
    }
}
