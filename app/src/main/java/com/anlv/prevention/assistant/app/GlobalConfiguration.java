/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anlv.prevention.assistant.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.anlv.prevention.assistant.BuildConfig;
import com.anlv.prevention.assistant.app.factory.SSLSocketFactoryImpl;
import com.anlv.prevention.assistant.app.utils.ConstantUtils;
import com.anlv.prevention.assistant.mvp.model.api.Api;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;

import java.lang.reflect.Modifier;
import java.security.KeyStore;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * ================================================
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 *
 * @see com.jess.arms.base.delegate.AppDelegate
 * @see com.jess.arms.integration.ManifestParser
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">请配合官方 Wiki 文档学习本框架</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog">更新日志, 升级必看!</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/Issues">常见 Issues, 踩坑必看!</a>
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">MVPArms 官方组件化方案 ArmsComponent, 进阶指南!</a>
 * Created by JessYan on 12/04/2017 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public final class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(@NonNull Context mainContext, @NonNull GlobalConfigModule.Builder mainBuilder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
            mainBuilder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }
        Utils.init(mainContext);

        try {
            SSLSocketFactoryImpl sslSocketFactory = new SSLSocketFactoryImpl(KeyStore.getInstance(KeyStore.getDefaultType()));
            mainBuilder.baseurl(BuildConfig.LOG_DEBUG ? Api.APP_DOMAIN_DEBUG : Api.APP_DOMAIN_RELEASE)
                    //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
                    .globalHttpHandler(new GlobalHttpHandlerImpl(mainContext))
                    //用来处理 RxJava 中发生的所有错误, RxJava 中发生的每个错误都会回调此接口
                    //RxJava 必须要使用 ErrorHandleSubscriber (默认实现 Subscriber 的 onError 方法), 此监听才生效
                    .responseErrorListener(new ResponseErrorListenerImpl())
                    .gsonConfiguration((context, builder) -> {//这里可以自己自定义配置 Gson 的参数
                        builder.serializeNulls()//支持序列化值为 null 的参数
                                .setDateFormat(ConstantUtils.PATTERN_DEFAULT)//自定义时间格式
                                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC)//指定序列化忽略成员
                                .enableComplexMapKeySerialization();//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                    })
                    .okhttpConfiguration((context, builder) -> {//这里可以自己自定义配置 Okhttp 的参数
                        builder.connectTimeout(15, TimeUnit.SECONDS)
                                .readTimeout(15, TimeUnit.SECONDS)
                                .writeTimeout(15, TimeUnit.SECONDS)
                                .sslSocketFactory(sslSocketFactory.getSSLContext().getSocketFactory(), sslSocketFactory.getTrustManager())
                                .hostnameVerifier((hostname, session) -> true);
                    })
                    .rxCacheConfiguration((context, builder) -> {//这里可以自己自定义配置 RxCache 的参数
                        builder.useExpiredDataIfLoaderNotAvailable(true);
                        return null;
                    });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
