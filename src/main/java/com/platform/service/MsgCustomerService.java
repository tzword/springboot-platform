package com.platform.service;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author jianghy
 * @Description: 消费者服务
 * @date 2020/11/26 14:37
 */
public interface MsgCustomerService {
    Boolean handlerMsg(MessageExt messageExt);
}
