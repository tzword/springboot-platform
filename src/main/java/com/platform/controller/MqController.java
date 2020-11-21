package com.platform.controller;

import com.platform.service.RabbitSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private RabbitSendService rabbitSendService;

    /**
     * 发送消息
     */
    @GetMapping("/sendMsg")
    public void sendMsg(){
        rabbitSendService.sendMsg();
    }
}
