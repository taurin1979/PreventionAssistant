package com.anlv.prevention.assistant.mvp.model.api.entity;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020/02/08
 *     desc   : 接口返回值基础类。
 * </pre>
 */
public class BaseResult<T> {
    private int code; // 执行结果
    private String message; // 异常信息
    private T result;

    public boolean isSucc() {
        return code == 200;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
