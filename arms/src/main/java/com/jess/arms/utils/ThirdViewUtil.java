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
package com.jess.arms.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jess.arms.base.Platform.DEPENDENCY_AUTO_LAYOUT;
import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_FRAMELAYOUT;
import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_LINEARLAYOUT;
import static com.jess.arms.base.delegate.ActivityDelegate.LAYOUT_RELATIVELAYOUT;

/**
 * ================================================
 * Created by JessYan on 17/03/2016 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ThirdViewUtil {
    private static int HAS_AUTO_LAYOUT_META = -1;//0 说明 AndroidManifest 里面没有使用 AutoLauout 的 Meta, 即不使用 AutoLayout, 1 为有 Meta, 即需要使用

    private ThirdViewUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static Unbinder bindTarget(Object target, Object source) {
        if (source instanceof Activity) {
            return ButterKnife.bind(target, (Activity) source);
        } else if (source instanceof View) {
            return ButterKnife.bind(target, (View) source);
        } else if (source instanceof Dialog) {
            return ButterKnife.bind(target, (Dialog) source);
        } else {
            return Unbinder.EMPTY;
        }
    }
}
