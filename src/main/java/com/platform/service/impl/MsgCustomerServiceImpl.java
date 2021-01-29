package com.platform.service.impl;

import com.platform.common.sysenum.MsgProductBean;
import com.platform.service.MsgCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * @author jianghy
 * @Description: 消息消费服务
 * @date 2020/11/26 14:38
 */
@Service
@Slf4j
public class MsgCustomerServiceImpl implements MsgCustomerService {
    @Override
    public Boolean handlerMsg(MessageExt messageExt) {
        log.info(MsgProductBean.ACCOUNT_INFO.getTagName() + "--> 消息:" + new String(messageExt.getBody()));
        return null;
    }
}
