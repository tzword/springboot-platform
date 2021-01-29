package com.platform.controller;

import com.platform.common.sysenum.MsgProductBean;
import com.platform.util.SendMsgUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author jianghy
 * @Description: rocketmq测试
 * @date 2020/11/26 14:57
 */
@RestController
@RequestMapping("/rocketmq")
public class RocketMqController {

    @Autowired
    private SendMsgUtil sendMsgUtil;

    /**
     * @Description: 发送普通消息
     * @param  1
     * @return java.lang.String
     * @throws
     * @author jianghy
     * @date 2020/11/26 16:44
     */
    @GetMapping("/send")
    public String sendRocketMq() {
        String accountNo = UUID.randomUUID().toString();
        SendResult sendResult = sendMsgUtil.sendMsg(accountNo, MsgProductBean.ACCOUNT_INFO.getTagName());
        return sendResult.toString();
    }


    /**
     * @Description: 发送延时消息
     * @param  1
     * @return java.lang.String
     * @throws
     * @author jianghy
     * @date 2020/11/26 16:44
     */
    @GetMapping("/sendDelay")
    public String sendRocketMqDdlay() {
        String accountNo = UUID.randomUUID().toString();
        SendResult sendResult = sendMsgUtil.sendMsgDelay(accountNo, MsgProductBean.ACCOUNT_INFO.getTagName());
        return sendResult.toString();
    }
}
