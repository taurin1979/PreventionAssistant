package com.anlv.prevention.assistant.app.utils;

import com.anlv.prevention.assistant.app.utils.adapter.StringNullAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2018/5/25
 *     desc   : JSON解析工具类。
 * </pre>
 */
public class JsonUtils {

    private static String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";//默认日期时间格式

    private static Gson gson = new GsonBuilder().serializeNulls()// 支持序列化null的参数
            .registerTypeAdapter(String.class, new StringNullAdapter()) // 序列化时将null替换成""
            .setDateFormat(PATTERN_DEFAULT)// 自定义时间格式
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC)
            .enableComplexMapKeySerialization()
            .create();

    private JsonUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static <T> T readValue(String content, Class<T> type) {
        T data = null;
        try {
            data = gson.fromJson(content, type);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return data;
    }

    public static <T> List<T> readArrayList(String content) {
        try {
            Type type = new TypeToken<ArrayList<T>>() {
            }.getType();
            return gson.fromJson(content, type);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return null;
    }

    public static <K, V> HashMap<K, V> readHasMap(String content) {
        try {
            Type type = new TypeToken<HashMap<K, V>>() {
            }.getType();
            return gson.fromJson(content, type);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return null;
    }

    public static <T> T readValue(String content, Type typeOfT) {
        try {
            return gson.fromJson(content, typeOfT);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return null;
    }

    public static String writeValueAsString(Object value) {
        return gson.toJson(value);
    }

    public static JSONObject getJson(JSONObject json, String key) {
        JSONObject value = null;
        try {
            value = json.getJSONObject(key);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static JSONArray getJsonArray(JSONObject json, String key) {
        JSONArray value = null;
        try {
            value = json.getJSONArray(key);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static String getStr(JSONObject json, String key) {
        String value = null;
        try {
            value = json.getString(key);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static boolean getBoolean(JSONObject json, String key) {
        boolean value = false;
        try {
            value = json.getBoolean(key);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static int getInt(JSONObject json, String key) {
        int value = 0;
        try {
            value = json.getInt(key);
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static long getLong(JSONObject json, String key) {
        long value = 0;
        try {
            value = Long.parseLong(json.getString(key));
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }

    public static float getFloat(JSONObject json, String key) {
        float value = 0;
        try {
            value = Float.parseFloat(json.getString(key));
        } catch (Exception e) {
            Timber.e(e, "json format error");
        }
        return value;
    }
}
