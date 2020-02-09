package com.anlv.prevention.assistant.mvp.model.api.entity;

import java.util.Date;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-07
 *     desc   : 采集信息。
 * </pre>
 */
public class Info {

    /**
     * 采集信息ID(32)
     */
    private String id;

    /**
     * 管控区名称(50)
     */
    private String areaName;

    /**
     * 证件号码(18)
     */
    private String certificateNumber;

    /**
     * 姓名(30)
     */
    private String name;

    /**
     * 电话号码(18)
     */
    private String phoneNumber;

    /**
     * 住址(100)
     */
    private String address;

    /**
     * 体温(10)
     */
    private float temperature;

    /**
     * 备注(50)
     */
    private String remark;

    /**
     * 上报时间
     */
    private Date createTime;

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

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
