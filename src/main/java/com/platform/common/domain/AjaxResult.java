package com.platform.common.domain;

import com.platform.common.sysenum.ResultTypeEnum;

import java.util.Date;
import java.util.HashMap;

/**
 * @author jianghy
 * @Description: ajax公用返回类
 * @date 2020/4/26 10:47
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 初始化一个新创建的 Message 对象
     */
    public AjaxResult() {
    }

    /**
     * @Description: 返回自定义错误信息
     * @param code 1
     * @param message 2
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:27
     */
    public static AjaxResult error(String code, String message) {
        AjaxResult json = new AjaxResult();
        json.put("resultCode", ResultTypeEnum.SYS_SUCCESS.getValue());
        json.put("resultMessage",ResultTypeEnum.SYS_SUCCESS.getDesc());
        json.put("timestamp",System.currentTimeMillis());
        AjaxResult ar = new AjaxResult();
        ar.put("code",code);
        ar.put("message",message);
        json.put("resultData",ar);
        return json;
    }


    /**
     * @Description: 成功返回数据
     * @param code 1
     * @param message 2
     * @param o 3
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:32
     */
    public static AjaxResult successData(String code, Object message,Object o) {
        AjaxResult json = new AjaxResult();
        json.put("resultCode", ResultTypeEnum.SYS_SUCCESS.getValue());
        json.put("resultMessage",ResultTypeEnum.SYS_SUCCESS.getDesc());
        json.put("timestamp",System.currentTimeMillis());
        AjaxResult ar = new AjaxResult();
        ar.put("code",code);
        ar.put("message",message);
        ar.put("data",o);
        json.put("resultData",ar);
        return json;
    }


    /**
     * @Description: 操作成功
     * @param  1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:39 
     */
    public static AjaxResult success() {
        AjaxResult json = new AjaxResult();
        json.put("resultCode", ResultTypeEnum.SYS_SUCCESS.getValue());
        json.put("resultMessage",ResultTypeEnum.SYS_SUCCESS.getDesc());
        json.put("timestamp",System.currentTimeMillis());
        AjaxResult ar = new AjaxResult();
        ar.put("code",ResultTypeEnum.BIZ_SUCCESS.getValue());
        ar.put("message",ResultTypeEnum.BIZ_SUCCESS.getDesc());
        json.put("resultData",ar);
        return json;
    }


    /**
     * @Description: 操作失败
     * @param  1
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:45
     */
    public static AjaxResult error() {
        AjaxResult json = new AjaxResult();
        json.put("resultCode", ResultTypeEnum.SYS_ERROR.getValue());
        json.put("resultMessage", ResultTypeEnum.SYS_ERROR.getDesc());
        json.put("timestamp", new Date());
        return json;
    }


    /**
     * 返回成功消息
     *
     * @param key   键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
