package com.platform.common.jg.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  @author: michael.jiang
 *  @Date: 2021/5/21 13:44
 *  @Description: 公共服务消费端
 */

@Component
@Slf4j
public class GgfwKafkaConsumer {

    /**
     * @description 巨潮上传s3接入流程
     *
     * 1.获取kafka的json数据
     * 2.监听announcement_info_202105的表的更新，
     * 3.判断如果isdownload是1，关联获取公告的信息，判断是不是A股
     * 4.调用算法，获取公告的类型和编码
     * 5.组装实体，插入到我们的公告表里

     * @author  michael.jiang
     * @param null
     * @return
     * @date    2021/5/21 13:46
     */

}



