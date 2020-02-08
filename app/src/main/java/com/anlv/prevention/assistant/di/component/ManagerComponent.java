package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.ManagerModule;
import com.anlv.prevention.assistant.mvp.contract.ManagerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.ManagerActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 18:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ManagerModule.class, dependencies = AppComponent.class)
public interface ManagerComponent {
    void inject(ManagerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ManagerComponent.Builder view(ManagerContract.View view);

        ManagerComponent.Builder appComponent(AppComponent appComponent);

        ManagerComponent build();
    }
}