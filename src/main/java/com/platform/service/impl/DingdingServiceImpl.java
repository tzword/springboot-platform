package com.platform.service.impl;

import com.platform.service.IDingService;
import com.platform.util.DingdingUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianghy
 * @since 2020-04-28
 */
@Slf4j
@Service
public class DingdingServiceImpl implements IDingService {

    @Value("${dingding.accessToken}")
    private String accessToken;
    @Value("${dingding.url}")
    private String url;
    @Autowired
    private DingdingUtil dingdingUtil;

    /**
     * @Description: 发送钉钉消息
     * @param list 1
     * @param content 2
     * @return com.dingtalk.api.response.OapiRobotSendResponse 
     * @throws
     * @author jianghy
     * @date 2021/1/27 17:14 
     */
    @Override
    public OapiRobotSendResponse sendMessage(List<String> list,String content) {
        try {
            String sign = "";
            String timestamp = "";
            Map<String, String> map = dingdingUtil.getSignInfo();
            if (!map.isEmpty()){
                sign = map.get("sign");
                timestamp = map.get("timestamp");
            }
            StringBuffer sbf  = new StringBuffer();
            sbf.append(url).append("?access_token=").append(accessToken).append("&timestamp=").append(timestamp).append("&sign=").append(sign);
            log.info("钉钉发送参数：{}",sbf);
            DingTalkClient client = new DefaultDingTalkClient(sbf.toString());
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            //采用markdown的样式
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();

            at.setAtMobiles(list);
            request.setAt(at);
            StringBuffer msbf  = new StringBuffer();
            list.forEach( m -> {
                msbf.append("@").append(m).append(",");
            });
            String s = msbf.toString();
            String substring = s.substring(0, s.length() - 1);
            markdown.setTitle("告警信息");
            markdown.setText(content + substring);
            log.info("钉钉发送信息内容：{}",content + substring);
            request.setMarkdown(markdown);
            return client.execute(request);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return null;
    }
}
