package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.GatherAddModule;
import com.anlv.prevention.assistant.mvp.contract.GatherAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.GatherAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GatherAddModule.class, dependencies = AppComponent.class)
public interface GatherAddComponent {
    void inject(GatherAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GatherAddComponent.Builder view(GatherAddContract.View view);

        GatherAddComponent.Builder appComponent(AppComponent appComponent);

        GatherAddComponent build();
    }
}