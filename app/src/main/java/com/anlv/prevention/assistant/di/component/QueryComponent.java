package com.anlv.prevention.assistant.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.anlv.prevention.assistant.di.module.QueryModule;
import com.anlv.prevention.assistant.mvp.contract.QueryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.anlv.prevention.assistant.mvp.ui.activity.QueryActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 20:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = QueryModule.class, dependencies = AppComponent.class)
public interface QueryComponent {
    void inject(QueryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        QueryComponent.Builder view(QueryContract.View view);

        QueryComponent.Builder appComponent(AppComponent appComponent);

        QueryComponent build();
    }
}