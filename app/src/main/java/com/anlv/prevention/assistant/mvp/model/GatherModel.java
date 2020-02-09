package com.anlv.prevention.assistant.mvp.model;

import android.app.Application;

import com.anlv.prevention.assistant.app.utils.GlobalUtils;
import com.anlv.prevention.assistant.mvp.contract.GatherContract;
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
 * Created by MVPArmsTemplate on 02/06/2020 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GatherModel extends BaseModel implements GatherContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    GatherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResult<String>> report(String certificateNumber, String name, String phoneNumber, String address, String temperature, String remark) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .report(GlobalUtils.gather.getAreaName(), certificateNumber, name,
                        phoneNumber, address, temperature, remark);
    }
}