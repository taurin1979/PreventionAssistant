package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;

import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.LoginContract;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.MAX_COUNT_TIME;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 14:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String mAppVersion;

    @Inject
    LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
        mAppVersion = String.format(Locale.getDefault(), "%s.%d",
                AppUtils.getAppVersionName(), AppUtils.getAppVersionCode());

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
     * 获取手机验证码，用于管理员注册验证
     */
    public void getPhoneCode(String phoneNumber) {
        mModel.smsVerification(phoneNumber)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        mRootView.getPhoneCodeSuccess();
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("验证码获取失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }

    /**
     * 重新获取验证码倒计时
     */
    public void countDown() {
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(MAX_COUNT_TIME)
                .compose(ToolUtils.applySchedulers(mRootView))
                //将递增数字替换成递减的倒计时数字
                .map(time -> MAX_COUNT_TIME - (time + 1))
                .subscribe(count -> mRootView.setCountDown(count));
    }

    /**
     * 采集员登录
     *
     * @param phoneNumber 电话号码
     * @param pinNumber   PIN码
     */
    public void gatherLogin(String phoneNumber, String pinNumber) {
        mModel.gatherLogin(phoneNumber, pinNumber, mAppVersion)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        GlobalUtils.loginType = 2;
                        GlobalUtils.sessionId = result.getResult();
                        SPUtils.getInstance().put("loginType", GlobalUtils.loginType);
                        SPUtils.getInstance().put("sessionId", GlobalUtils.sessionId);
                        userInfo();
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("登录失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }

    /**
     * 管理员登录
     *
     * @param phoneNumber 电话号码
     * @param phoneCode   验证码
     */
    public void managerLogin(String phoneNumber, String phoneCode) {
        mModel.gatherLogin(phoneNumber, phoneCode, mAppVersion)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        GlobalUtils.loginType = 1;
                        GlobalUtils.sessionId = result.getResult();
                        SPUtils.getInstance().put("loginType", GlobalUtils.loginType);
                        SPUtils.getInstance().put("sessionId", GlobalUtils.sessionId);
                        userInfo();
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("登录失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }

    /**
     * 用户信息获取
     */
    private void userInfo() {
        mModel.gatherInfo()
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result.isSucc()) {
                        GlobalUtils.gather = result.getResult();
                        mRootView.loginSuccess();
                    } else {
                        if (ObjectUtils.isEmpty(result.getMessage())) {
                            mRootView.showMessage("登录失败");
                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                }, Timber::e);
    }
}
