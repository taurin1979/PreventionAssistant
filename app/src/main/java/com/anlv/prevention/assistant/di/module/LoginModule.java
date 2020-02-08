package com.anlv.prevention.assistant.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.anlv.prevention.assistant.mvp.contract.LoginContract;
import com.anlv.prevention.assistant.mvp.model.LoginModel;


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
@Module
public abstract class LoginModule {

    @Binds
    abstract LoginContract.Model bindLoginModel(LoginModel model);
}