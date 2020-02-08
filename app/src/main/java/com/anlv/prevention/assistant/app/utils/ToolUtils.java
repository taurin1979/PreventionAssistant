package com.anlv.prevention.assistant.app.utils;

import android.view.View;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-06
 *     desc   :
 * </pre>
 */
public class ToolUtils {
    private final static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=".toCharArray();

    private static int lastClickId = 0;//控件Id
    private static long lastClickTime = 0;//控件最后一次点击的时间，用来防止短时间内多次点击


    // 判断是否多次点击控件
    public static boolean isFastDoubleClick(@NotNull View view) {
        long time = System.currentTimeMillis();
        long diff = time - lastClickTime;
        if (lastClickId == view.getId()) {
            if (diff > 0 && diff < 500) {
                lastClickTime = time;
                return true;
            }
        }
        lastClickId = view.getId();
        lastClickTime = time;
        return false;
    }

    /**
     * 创建一个22位的UUID字符串
     */
    public static String createUUID() {
        UUID uuid = Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
        long mostBits = uuid.getMostSignificantBits();
        long leastBits = uuid.getLeastSignificantBits();
        char[] out = new char[24];
        int tmp, idx = 0;
        int bit = 0, bt1 = 8, bt2 = 8;
        int mask, offset_m;
        for (; bit < 16; bit += 3, idx += 4) {
            offset_m = 64 - (bit + 3) * 8;
            int offset_l;
            tmp = 0;

            if (bt1 > 3) {
                mask = (1 << 8 * 3) - 1;
            } else if (bt1 >= 0) {
                mask = (1 << 8 * bt1) - 1;
                bt2 -= 3 - bt1;
            } else {
                mask = (1 << 8 * ((bt2 > 3) ? 3 : bt2)) - 1;
                bt2 -= 3;
            }
            if (bt1 > 0) {
                bt1 -= 3;
                tmp = (int) ((offset_m < 0) ? mostBits : (mostBits >>> offset_m) & mask);
                if (bt1 < 0) {
                    tmp <<= Math.abs(offset_m);
                    mask = (1 << 8 * Math.abs(bt1)) - 1;
                }
            }
            if (offset_m < 0) {
                offset_l = 64 + offset_m;
                tmp |= ((offset_l < 0) ? leastBits : (leastBits >>> offset_l)) & mask;
            }

            if (bit == 15) {
                out[idx + 3] = alphabet[64];
                out[idx + 2] = alphabet[64];
                tmp <<= 4;
            } else {
                out[idx + 3] = alphabet[tmp & 0x3f];
                tmp >>= 6;
                out[idx + 2] = alphabet[tmp & 0x3f];
                tmp >>= 6;
            }
            out[idx + 1] = alphabet[tmp & 0x3f];
            tmp >>= 6;
            out[idx] = alphabet[tmp & 0x3f];
        }
        return new String(out, 0, 22);
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return observable -> //隐藏进度条
                observable.subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                            Timber.i("显示进度条");
                            view.showLoading();//显示进度条
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(view::hideLoading).compose(RxLifecycleUtils.bindToLifecycle(view));
    }
}
