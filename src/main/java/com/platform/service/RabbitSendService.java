package com.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.config.RabbitConfig;
import com.platform.entity.User;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitSendService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendMsg(){
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setAboutme("你好");
        rabbitTemplate.convertAndSend(RabbitConfig.orderExchange, RabbitConfig.orderRoutingKey,
                JSONObject.toJSONString(user), message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    message.getMessageProperties().setExpiration("20000");
                    return message;
                });
    }

}
