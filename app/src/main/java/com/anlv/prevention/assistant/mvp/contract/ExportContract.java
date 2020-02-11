package com.anlv.prevention.assistant.mvp.contract;

import android.app.Activity;

import com.anlv.prevention.assistant.mvp.model.ExportModel;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;
import com.anlv.prevention.assistant.mvp.ui.activity.ExportActivity;
import com.anlv.prevention.assistant.mvp.ui.adapter.InfoAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


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
public interface ExportContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 申请权限
         * {@link ExportActivity#getActivity()}
         */
        Activity getActivity();

        /**
         * 申请权限
         * {@link ExportActivity#getRxPermissions()}
         */
        RxPermissions getRxPermissions();

        /**
         * 设置采集信息列表适配器
         * {@link ExportActivity#setAdapter(InfoAdapter)}
         */
        void setAdapter(InfoAdapter adapter);

        /**
         * 数据导出失败
         * {@link ExportActivity#exportFail(File)}
         */
        void exportFail(File file);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 采集数据查询
         * {@link ExportModel#queryData(String, String)}
         */
        Observable<BaseResult<List<Info>>> queryData(String beginTime,
                                                     String endTime);
    }
}
