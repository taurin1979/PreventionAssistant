package com.anlv.prevention.assistant.mvp.model.api.entity;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 管控区对象
 * </pre>
 */
public class Area {
    /**
     * 管控区ID(32)
     */
    private String id;

    /**
     * 管控区名称(50)
     */
    private String areaName;

    /**
     * 管控区PIN码(6)
     */
    private String pinCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
