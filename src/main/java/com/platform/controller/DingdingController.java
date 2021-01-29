package com.platform.controller;

import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import com.platform.req.DingdingReq;
import com.platform.service.IDingService;
import com.dingtalk.api.response.OapiRobotSendResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jianghy
 * @Description: 钉钉应用
 * @date 2021/1/27 15:06
 */
@Slf4j
@RestController
@RequestMapping("/dingding")
@Api(value = "钉钉告警", tags = {"钉钉告警"})
public class DingdingController {
    @Autowired
    private IDingService iDingService;


    /**
     * @param dingdingReq 1
     * @return com.chinaunicom.common.domain.AjaxResult
     * @throws
     * @Description: 发送钉钉告警消息
     * @author jianghy
     * @date 2021/1/27 16:56
     */
    @PostMapping("/sendMessage")
    @ApiOperation(value = "发送钉钉消息", notes = "发送钉钉消息")
    public AjaxResult sendMessage(@Validated @RequestBody DingdingReq dingdingReq) {
        List<String> list = dingdingReq.getMobiles();
        String content = dingdingReq.getContent();
        OapiRobotSendResponse oapiRobotSendResponse = iDingService.sendMessage(list,content);
        if (null != oapiRobotSendResponse && "0".equals(oapiRobotSendResponse.getErrcode())) {
            return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(), ResultTypeEnum.BIZ_SUCCESS.getDesc(), oapiRobotSendResponse);
        }else{
            return AjaxResult.errorMsg(ResultTypeEnum.BIZ_MSG_ERROR.getValue(), ResultTypeEnum.BIZ_MSG_ERROR.getDesc());
        }
    }


}
