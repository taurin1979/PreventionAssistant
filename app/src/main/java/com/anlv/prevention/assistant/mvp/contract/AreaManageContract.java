package com.anlv.prevention.assistant.mvp.contract;

import com.anlv.prevention.assistant.mvp.model.AreaManageModel;
import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.ui.activity.AreaManageActivity;
import com.anlv.prevention.assistant.mvp.ui.adapter.AreaAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;


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
public interface AreaManageContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 设置采集信息列表适配器
         * {@link AreaManageActivity#setAdapter(AreaAdapter)}
         */
        void setAdapter(AreaAdapter adapter);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 查询管控区
         * {{@link AreaManageModel#queryArea()}}
         */
        Observable<BaseResult<List<Area>>> queryArea();

        /**
         * 删除管控区
         * {{@link AreaManageModel#delArea(String)}}
         */
        Observable<BaseResult<String>> delArea(String id);
    }
}
