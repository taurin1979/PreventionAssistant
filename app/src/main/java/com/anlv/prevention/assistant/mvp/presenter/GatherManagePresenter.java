package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.GatherManageContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.ui.adapter.GatherAdapter;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

import static com.anlv.prevention.assistant.app.utils.ConstantUtils.EVENT_GATHER_ADD;
import static com.anlv.prevention.assistant.app.utils.ConstantUtils.EVENT_GATHER_EDIT;


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
public class GatherManagePresenter extends BasePresenter<GatherManageContract.Model, GatherManageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private GatherAdapter mAdapter;

    @Inject
    GatherManagePresenter(GatherManageContract.Model model, GatherManageContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message message) {
        switch (message.what) {
            case EVENT_GATHER_ADD:
                if (ObjectUtils.isNotEmpty(mAdapter)) {
                    mAdapter.insertItem((Gather) message.obj);
                }
                break;
            case EVENT_GATHER_EDIT:
                if (ObjectUtils.isNotEmpty(mAdapter)) {
                    mAdapter.updateItem((Gather) message.obj);
                }
                break;
        }
    }

    public void refresh(String key) {
        mModel.queryGather(key)
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<List<Gather>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<List<Gather>> result) {
                        if (result.isSucc()) {
                            if (ObjectUtils.isEmpty(mAdapter)) {
                                mAdapter = new GatherAdapter(result.getResult());
                                mRootView.setAdapter(mAdapter);
                            } else {
                                mAdapter.updateDataList(result.getResult());
                            }
                        } else {
                            if (ObjectUtils.isEmpty(result.getMessage())) {
                                mRootView.showMessage("采集员查询失败");
                            } else {
                                mRootView.showMessage(result.getMessage());
                            }
                        }
                    }
                });
    }

    public void gatherDelete(Gather gather) {
        mModel.delGather(gather.getId())
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<String> result) {
                        if (result.isSucc()) {
                            mAdapter.deleteItem(gather);
                            mRootView.showMessage("采集员删除成功");
                        } else {
                            if (ObjectUtils.isEmpty(result.getMessage())) {
                                mRootView.showMessage("采集员删除失败");
                            } else {
                                mRootView.showMessage(result.getMessage());
                            }
                        }
                    }
                });
    }
}
