package com.platform.util;


import com.platform.common.listener.MsgListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jianghy
 * @Description: rocketmq消费者工具类
 * @date 2020/11/26 14:33
 */
@Configuration
@Slf4j
public class MsgConsumerUtil {

    @Value("${rocketmq.name-server}")
    private String nameSer;
    @Value("${rocketmq.producer.group}")
    private String consumerGroup;
    @Value("${rocketmq.topic-key}")
    private String topic;

    @Autowired
    MsgListener msgListener;

    @Bean
    public DefaultMQPushConsumer startConsumer(){
        log.info("消费者开始");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameSer);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(msgListener);
        try {
            consumer.subscribe(topic,"*");
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("消费者结束");
        return consumer;
    }
}

