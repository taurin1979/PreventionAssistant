package com.anlv.prevention.assistant.mvp.contract;

import com.anlv.prevention.assistant.mvp.model.GatherManageModel;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.ui.activity.GatherManageActivity;
import com.anlv.prevention.assistant.mvp.ui.adapter.GatherAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


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
public interface GatherManageContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 设置采集信息列表适配器
         * {@link GatherManageActivity#setAdapter(GatherAdapter)}
         */
        void setAdapter(GatherAdapter adapter);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 采集员添加
         * {@link GatherManageModel#queryGather(String)}
         */
        Observable<BaseResult<List<Gather>>> queryGather(String key);

        /**
         * 采集员添加
         * {@link GatherManageModel#delGather(String)}
         */
        Observable<BaseResult<String>> delGather(String id);
    }
}
