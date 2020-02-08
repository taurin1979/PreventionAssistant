package com.anlv.prevention.assistant.mvp.contract;

import com.anlv.prevention.assistant.mvp.model.LoginModel;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.ui.activity.LoginActivity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import retrofit2.http.POST;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/06/2020 14:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 手机验证码获取成功，用此方法通知视图
         * {@link LoginActivity#getPhoneCodeSuccess()}
         */
        void getPhoneCodeSuccess();

        /**
         * 设置倒计时
         * {@link LoginActivity#setCountDown(long)}
         */
        void setCountDown(long count);

        /**
         * 采集员登录成功
         * {@link LoginActivity#loginSuccess()}
         */
        void loginSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 采集员登录
         * {@link LoginModel#gatherLogin(String, String, String)}
         */
        @POST("user/loginUser")
        Observable<BaseResult<String>> gatherLogin(String phoneNumber,
                                                   String code,
                                                   String version);

        /**
         * 管理员登录
         * {@link LoginModel#managerLogin(String, String, String)}
         */
        @POST("user/loginAdmin")
        Observable<BaseResult<String>> managerLogin(String phoneNumber,
                                                    String code,
                                                    String version);

        /**
         * 用户信息
         * {@link LoginModel#gatherInfo()}
         */
        Observable<BaseResult<Gather>> gatherInfo();

        /**
         * 短信验证码获取
         * {@link LoginModel#smsVerification(String)}
         */
        Observable<BaseResult<String>> smsVerification(String phoneNumber);


    }
}
