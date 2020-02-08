package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.ExportModule;
import com.anlv.prevention.assistant.mvp.contract.ExportContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.ExportActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ExportModule.class, dependencies = AppComponent.class)
public interface ExportComponent {
    void inject(ExportActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExportComponent.Builder view(ExportContract.View view);

        ExportComponent.Builder appComponent(AppComponent appComponent);

        ExportComponent build();
    }
}