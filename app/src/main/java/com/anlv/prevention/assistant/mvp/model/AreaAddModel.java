package com.anlv.prevention.assistant.mvp.model;

import android.app.Application;

import com.anlv.prevention.assistant.mvp.contract.AreaAddContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.service.CommonService;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class AreaAddModel extends BaseModel implements AreaAddContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    AreaAddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 管控区添加
     * {@link CommonService#addArea(String, String)}
     */
    @Override
    public Observable<BaseResult<Area>> addArea(String name, String pin) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .addArea(name, pin);
    }
}