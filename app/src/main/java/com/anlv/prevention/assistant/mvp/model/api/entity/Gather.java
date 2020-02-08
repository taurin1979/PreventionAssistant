package com.anlv.prevention.assistant.mvp.model.api.entity;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 采集员对象
 * </pre>
 */
public class Gather {
    /**
     * 采集员ID(32)
     */
    private String id;

    /**
     * 采集员名称(30)
     */
    private String name;

    /**
     * 采集员手机号码(18)
     */
    private String phoneNumber;

    /**
     * 所属管控区ID(32)
     */
    private String areaId;

    /**
     * 所属管控区名称(50)
     */
    private String areaName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
