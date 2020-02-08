package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.GatherModule;
import com.anlv.prevention.assistant.mvp.contract.GatherContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.GatherActivity;


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
@Component(modules = GatherModule.class, dependencies = AppComponent.class)
public interface GatherComponent {
    void inject(GatherActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GatherComponent.Builder view(GatherContract.View view);

        GatherComponent.Builder appComponent(AppComponent appComponent);

        GatherComponent build();
    }
}