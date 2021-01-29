package com.platform.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jianghy
 * @Description: rocketmq工具类
 * @date 2020/11/26 14:29
 */
@Component
@Slf4j
public class SendMsgUtil {

    @Value("${rocketmq.topic-key}")
    private String topic;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    public SendResult sendMsg(String msg, String tagName) {
        Message message = new Message(topic, tagName, msg.getBytes());
        try {
            SendResult sendResult = defaultMQProducer.send(message);
            log.info("输出生产者信息={}", sendResult);
            return sendResult;
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SendResult sendMsgDelay(String msg, String tagName) {
        Message message = new Message(topic, tagName, msg.getBytes());
        // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        // 这里设置4，即30s的延迟
        message.setDelayTimeLevel(4);
        try {
            SendResult sendResult = defaultMQProducer.send(message);
            log.info("输出生产者信息={}", sendResult);
            return sendResult;
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}