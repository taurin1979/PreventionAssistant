package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.GatherEditModule;
import com.anlv.prevention.assistant.mvp.contract.GatherEditContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.GatherEditActivity;


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
@Component(modules = GatherEditModule.class, dependencies = AppComponent.class)
public interface GatherEditComponent {
    void inject(GatherEditActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GatherEditComponent.Builder view(GatherEditContract.View view);

        GatherEditComponent.Builder appComponent(AppComponent appComponent);

        GatherEditComponent build();
    }
}