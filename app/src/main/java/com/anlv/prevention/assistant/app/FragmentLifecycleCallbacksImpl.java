/*
 * Copyright 2018 JessYan
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

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.RefWatcher;

import java.util.Objects;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link FragmentManager.FragmentLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 23/08/2018 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        Timber.i("%s - onFragmentCreated", f.toString());
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        Timber.i("%s - onFragmentDestroyed", f.toString());
        ((RefWatcher) Objects.requireNonNull(ArmsUtils
                .obtainAppComponentFromContext(f.getActivity())
                .extras()
                .get(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName()))))
                .watch(f);
    }
}
