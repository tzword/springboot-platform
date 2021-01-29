package com.platform.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianghy
 * @Description: 钉钉工具类
 * @date 2021/1/27 16:29
 */
@Component
public class DingdingUtil {
    @Value("${dingding.secret}")
    private String secret;

    public Map<String,String> getSignInfo() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
        Map<String,String> map = new HashMap<>();
        map.put("sign",sign);
        map.put("timestamp",timestamp.toString());
        return map;
    }
}
