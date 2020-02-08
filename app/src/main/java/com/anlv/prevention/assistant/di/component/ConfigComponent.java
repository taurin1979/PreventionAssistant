package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.ConfigModule;
import com.anlv.prevention.assistant.mvp.contract.ConfigContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.ConfigActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 11:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ConfigModule.class, dependencies = AppComponent.class)
public interface ConfigComponent {
    void inject(ConfigActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConfigComponent.Builder view(ConfigContract.View view);

        ConfigComponent.Builder appComponent(AppComponent appComponent);

        ConfigComponent build();
    }
}