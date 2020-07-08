package com.platform.common.sign;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jianghy
 * @Description: SHA1工具类
 * @date 2020/6/28 11:03
 */
public class SHA1 {
    private static final Logger log = LoggerFactory.getLogger(SHA1.class);

    public static String encrypt(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(new String(str.getBytes("iso8859-1"), "utf-8").getBytes());
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("SHA1加密异常:", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("SHA1加密异常:", e);
        }
        return str;
    }

    @SuppressWarnings("unchecked")
    public static String encrypt(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj instanceof Map) {
            map = (Map<String, Object>) obj;
        } else {
            map = BeanUtil.transBean2Map(obj);
        }
        map.remove("sign");
        map.remove("encrypt");
        String result = BeanUtil.mapOrderStr(map);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return encrypt(result);
    }

    /**
     * @Description: 验证签名
     * @param obj 1 
     * @return boolean 
     * @throws
     * @author jianghy
     * @date 2020/7/8 10:12 
     */
    @SuppressWarnings("unchecked")
    public static boolean check(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return false;
        }
        if (obj instanceof Map) {
            map = (Map<String, Object>) obj;
        } else {
            map = BeanUtil.transBean2Map(obj);
        }
        String sign = (String) map.get("sign");
        if (sign == null) {
            return false;
        }
        String str = encrypt(obj);
        log.info("传过来的签名: {}",sign);
        log.info("验证后的签名: {}",str);
        return sign.equals(str) ? true : false;


    }


}

