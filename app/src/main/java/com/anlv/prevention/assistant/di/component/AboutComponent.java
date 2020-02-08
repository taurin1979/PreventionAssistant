package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.AboutModule;
import com.anlv.prevention.assistant.mvp.contract.AboutContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.AboutActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 11:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AboutModule.class, dependencies = AppComponent.class)
public interface AboutComponent {
    void inject(AboutActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AboutComponent.Builder view(AboutContract.View view);

        AboutComponent.Builder appComponent(AppComponent appComponent);

        AboutComponent build();
    }
}