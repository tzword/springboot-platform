package com.platform.controller;

import com.platform.common.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * 测试kafka
     */
    @GetMapping("send")
    public String send(){
        kafkaProducer.send("这个是一条消息");
        return "发送成功";
    }
}
