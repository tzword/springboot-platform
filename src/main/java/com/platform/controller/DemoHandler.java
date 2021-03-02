package com.platform.controller;

/**
 * @author jianghy
 * @Description:
 * @date 2021/2/20 14:14
 */

import com.platform.service.DemoJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.xxl.job.core.biz.model.ReturnT.FAIL;
import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

@Component
@Slf4j
public class DemoHandler {

    @Autowired
    private  DemoJobService demoJobService;

    @XxlJob("demoHandler")
    public ReturnT<String> execute(String s1) throws Exception {
        XxlJobLogger.log("xxl-job测试任务开始执行。【args: {}】", s1);
        try {
            demoJobService.demoTest(s1);
            XxlJobLogger.log("xxl-job测试任务执行结束。");
            return SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log("xxl-job测试任务执行出错!", e);
            return FAIL;
        }
    }
}