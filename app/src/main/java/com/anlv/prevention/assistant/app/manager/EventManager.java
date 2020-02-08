package com.anlv.prevention.assistant.app.manager;

import android.os.Message;

import com.anlv.prevention.assistant.app.utils.JsonUtils;
import com.blankj.utilcode.util.ObjectUtils;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2019/09/06
 *     desc   : 消息发送管理器。
 * </pre>
 */
public class EventManager {

    private EventConverter eventConverter = null;

    public void setEventConverter(EventConverter eventConverter) {
        this.eventConverter = eventConverter;
    }

    public void sendMessage(int type) {
        sendMessage(type, null);
    }

    public void sendMessage(int type, Object obj) {
        if (ObjectUtils.isNotEmpty(eventConverter)) {
            if (ObjectUtils.isEmpty(obj)) {
                Timber.i("[EventBus]%s", eventConverter.convertDescribe(type));
            } else {
                Timber.i("[EventBus]%s:%s", eventConverter.convertDescribe(type), JsonUtils.writeValueAsString(obj));
            }
        } else {
            if (ObjectUtils.isEmpty(obj)) {
                Timber.i("[EventBus]%d", type);
            } else {
                Timber.i("[EventBus]%d:%s", type, JsonUtils.writeValueAsString(obj));
            }
        }
        Message message = new Message();
        message.what = type;
        message.obj = obj;
        EventBus.getDefault().post(message);
    }

    private EventManager() {}

    public static EventManager getInstance() {
        return EventManager.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public interface EventConverter {
        String convertDescribe(int type);
    }
}
