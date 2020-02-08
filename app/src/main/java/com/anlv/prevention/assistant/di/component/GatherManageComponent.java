package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.GatherManageModule;
import com.anlv.prevention.assistant.mvp.contract.GatherManageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.GatherManageActivity;


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
@Component(modules = GatherManageModule.class, dependencies = AppComponent.class)
public interface GatherManageComponent {
    void inject(GatherManageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GatherManageComponent.Builder view(GatherManageContract.View view);

        GatherManageComponent.Builder appComponent(AppComponent appComponent);

        GatherManageComponent build();
    }
}