package com.anlv.prevention.assistant.mvp.model;

import android.app.Application;

import com.anlv.prevention.assistant.mvp.contract.GatherManageContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.model.api.service.CommonService;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class GatherManageModel extends BaseModel implements GatherManageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    GatherManageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResult<List<Gather>>> queryGather(String key) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .queryGather(key);
    }

    @Override
    public Observable<BaseResult<String>> delGather(String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .delGather(id);
    }
}