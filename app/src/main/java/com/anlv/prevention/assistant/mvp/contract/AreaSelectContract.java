package com.anlv.prevention.assistant.mvp.contract;

import com.anlv.prevention.assistant.mvp.model.AreaSelectModel;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.ui.activity.AreaSelectActivity;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaSelectAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2020 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AreaSelectContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 设置采集信息列表适配器
         * {@link AreaSelectActivity#setAdapter(AreaSelectAdapter)}
         */
        void setAdapter(AreaSelectAdapter adapter);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取管控区列表
         * {@link AreaSelectModel#getAreaList}
         */
        Observable<BaseResult<List<Area>>> getAreaList();
    }
}
