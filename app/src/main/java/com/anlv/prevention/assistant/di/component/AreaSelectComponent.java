package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.AreaSelectModule;
import com.anlv.prevention.assistant.mvp.contract.AreaSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.AreaSelectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2020 10:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AreaSelectModule.class, dependencies = AppComponent.class)
public interface AreaSelectComponent {
    void inject(AreaSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AreaSelectComponent.Builder view(AreaSelectContract.View view);

        AreaSelectComponent.Builder appComponent(AppComponent appComponent);

        AreaSelectComponent build();
    }
}