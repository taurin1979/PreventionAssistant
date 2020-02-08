package com.anlv.prevention.assistant.mvp.model.api.service;

import com.anlv.prevention.assistant.mvp.model.api.entity.Area;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Gather;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2020-02-08
 *     desc   : 接口定义
 * </pre>
 */
public interface CommonService {
    /**
     * 采集员登录
     *
     * @param phoneNumber   手机号码(18)
     * @param code          PIN码(6)
     * @param clientType    客户端类型 1.安卓,2.苹果
     * @param clientVersion 客户端版本
     * @return sessionId
     */
    @POST("user/loginUser")
    Observable<BaseResult<String>> gatherLogin(@Query("phoneNumber") String phoneNumber,
                                               @Query("code") String code,
                                               @Query("clientType") int clientType,
                                               @Query("clientVersion") String clientVersion);

    /**
     * 管理员登录
     *
     * @param phoneNumber   手机号码(18)
     * @param code          验证码(6)
     * @param clientType    客户端类型 1.安卓,2.苹果
     * @param clientVersion 客户端版本
     * @return sessionId
     */
    @POST("user/loginAdmin")
    Observable<BaseResult<String>> managerLogin(@Query("phoneNumber") String phoneNumber,
                                                @Query("code") String code,
                                                @Query("clientType") int clientType,
                                                @Query("clientVersion") String clientVersion);

    /**
     * 用户信息
     *
     * @return 当前用户对象
     */
    @POST("user/info")
    Observable<BaseResult<Gather>> gatherInfo();

    /**
     * 用户登出
     */
    @POST("user/logout")
    Observable<BaseResult<String>> userLogout();

    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号码(18)
     * @param type        使用类型 1.管理员登录
     */
    @POST("sms/verification")
    Observable<BaseResult<String>> smsVerification(@Query("phoneNumber") String phoneNumber,
                                                   @Query("type") int type);

    /**
     * 体温上报
     *
     * @param areaName          管控区名称(50)
     * @param certificateNumber 证件号码(18)
     * @param name              姓名(30)
     * @param phoneNumber       手机号码(18)
     * @param address           住址(100)
     * @param temperature       体温(10)
     * @param remark            备注(50)
     */
    @POST("report/temperature")
    Observable<BaseResult<String>> report(@Query("areaName") String areaName,
                                          @Query("certificateNumber") String certificateNumber,
                                          @Query("name") String name,
                                          @Query("phoneNumber") String phoneNumber,
                                          @Query("address") String address,
                                          @Query("temperature") String temperature,
                                          @Query("remark") String remark);

    /**
     * 身份查询
     *
     * @param certificateNumber 证件号码(18)
     * @return 采集信息对象列表
     */
    @POST("report/queryRecent")
    Observable<BaseResult<List<Info>>> queryRecent(@Query("certificateNumber") String certificateNumber);

    /**
     * 数据查询
     *
     * @param beginTime 开始时间(大于等于)
     * @param endTime   结束时间(小于等于)
     * @return 采集信息对象列表
     */
    @POST("report/queryData")
    Observable<BaseResult<List<Info>>> queryData(@Query("beginTime") String beginTime,
                                                 @Query("endTime") String endTime);

    /**
     * 采集员查询
     *
     * @param key 关键字(18) PS:姓名或手机号码
     * @return 采集员对象列表
     */
    @POST("user/collector/query")
    Observable<BaseResult<List<Gather>>> queryGather(@Query("key") String key);

    /**
     * 采集员添加
     *
     * @param areaId      管控区ID(32)
     * @param name        姓名(30)
     * @param phoneNumber 手机号码(18)
     * @param remark      备注(50)
     * @return
     */
    @POST("user/collector/add")
    Observable<BaseResult<Gather>> addGather(@Query("areaId") String areaId,
                                             @Query("name") String name,
                                             @Query("phoneNumber") String phoneNumber,
                                             @Query("remark") String remark);

    /**
     * 采集员更新
     *
     * @param id          采集员ID(32)
     * @param areaId      管控区ID(32)
     * @param name        姓名(30)
     * @param phoneNumber 手机号码(18)
     * @param remark      备注(50)
     * @return
     */
    @POST("user/collector/update")
    Observable<BaseResult<Gather>> updateGather(@Query("id") String id,
                                                @Query("areaId") String areaId,
                                                @Query("name") String name,
                                                @Query("phoneNumber") String phoneNumber,
                                                @Query("remark") String remark);

    /**
     * 采集员删除
     *
     * @param id 采集员ID(32)
     */
    @POST("user/collector/delete")
    Observable<BaseResult<String>> delGather(@Query("id") String id);

    /**
     * 管控区查询
     *
     * @return 管控区对象列表
     */
    @POST("area/query")
    Observable<BaseResult<List<Area>>> queryArea();

    /**
     * 管控区添加
     *
     * @param areaName 管控区名称(50)
     * @param pinCode  管控区PIN码(6)
     * @return 管控区对象
     */
    @POST("area/add")
    Observable<BaseResult<Area>> addArea(@Query("areaName") String areaName,
                                         @Query("pinCode") String pinCode);

    /**
     * 管控区更新
     *
     * @param id       管控区ID(32)
     * @param areaName 管控区名称(50)
     * @param pinCode  管控区PIN码(6)
     * @return 管控区对象
     */
    @POST("area/update")
    Observable<BaseResult<Area>> updateArea(@Query("id") String id,
                                            @Query("areaName") String areaName,
                                            @Query("pinCode") String pinCode);

    /**
     * 管控区删除
     *
     * @param id 管控区ID(32)
     */
    @POST("area/delete")
    Observable<BaseResult<String>> delArea(@Query("id") String id);
}
