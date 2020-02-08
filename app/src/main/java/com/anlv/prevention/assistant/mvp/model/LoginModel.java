package com.anlv.prevention.assistant.mvp.model;

import android.app.Application;

import com.anlv.prevention.assistant.mvp.contract.LoginContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.model.api.service.CommonService;
import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResult<String>> gatherLogin(String phoneNumber, String code, String version) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .gatherLogin(phoneNumber, code, 1, version);
    }

    @Override
    public Observable<BaseResult<String>> managerLogin(String phoneNumber, String code, String version) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .managerLogin(phoneNumber, code, 1, version);
    }

    @Override
    public Observable<BaseResult<Gather>> gatherInfo() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .gatherInfo();
    }

    @Override
    public Observable<BaseResult<String>> smsVerification(String phoneNumber) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .smsVerification(phoneNumber, 1);
    }
}