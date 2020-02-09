package com.anlv.prevention.assistant.di.module;

import androidx.fragment.app.FragmentActivity;

import com.anlv.prevention.assistant.mvp.contract.ExportContract;
import com.anlv.prevention.assistant.mvp.model.ExportModel;
import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
@Module
public abstract class ExportModule {

    @Binds
    abstract ExportContract.Model bindExportModel(ExportModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(ExportContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}