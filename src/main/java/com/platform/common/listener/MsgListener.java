package com.platform.common.listener;

import com.platform.service.MsgCustomerService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jianghy
 * @Description: rocketmq监听器
 * @date 2020/11/26 14:36
 */
@Component
public class MsgListener implements MessageListenerConcurrently {

    @Autowired
    private MsgCustomerService msgCustomerService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        msgs.forEach(messageExt -> {
            msgCustomerService.handlerMsg(messageExt);
        });
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
