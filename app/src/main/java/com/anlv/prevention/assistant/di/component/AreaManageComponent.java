package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.AreaManageModule;
import com.anlv.prevention.assistant.mvp.contract.AreaManageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.AreaManageActivity;


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
@Component(modules = AreaManageModule.class, dependencies = AppComponent.class)
public interface AreaManageComponent {
    void inject(AreaManageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AreaManageComponent.Builder view(AreaManageContract.View view);

        AreaManageComponent.Builder appComponent(AppComponent appComponent);

        AreaManageComponent build();
    }
}