package com.platform.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class RabbitReviceService {

    /**
     * 消费消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = "dlx-queue")
    public void dlxListener(Message message, Channel channel) throws IOException {
        System.out.println(new String(message.getBody()));

        //对消息进行业务处理....
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
