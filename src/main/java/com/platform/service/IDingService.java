package com.platform.service;

import com.dingtalk.api.response.OapiRobotSendResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianghy
 * @since 2020-04-28
 */
public interface IDingService {


    //发送钉钉消息
    OapiRobotSendResponse sendMessage(List<String> list, String content);
}
