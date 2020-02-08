package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.AreaEditModule;
import com.anlv.prevention.assistant.mvp.contract.AreaEditContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.AreaEditActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AreaEditModule.class, dependencies = AppComponent.class)
public interface AreaEditComponent {
    void inject(AreaEditActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AreaEditComponent.Builder view(AreaEditContract.View view);

        AreaEditComponent.Builder appComponent(AppComponent appComponent);

        AreaEditComponent build();
    }
}