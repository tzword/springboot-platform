package com.platform.common.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author jianghy
 * @Description: 生产者配置
 * @date 2020/11/26 13:44
 */
@Component
public class DefaultProductConfig {

    //生产者组
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Bean
    public DefaultMQProducer getProduct(){
        //示例生产者
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr(nameServer);
        return producer;
    }

}
