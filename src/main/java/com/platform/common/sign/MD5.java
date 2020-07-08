package com.platform.common.sign;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jianghy
 * @Description: md5加密工具类
 * @date 2020/6/28 11:02
 */
public class MD5 {


    private static final Logger log = LoggerFactory.getLogger(MD5.class);

    public static String PRIVATE_KEY = "这是你的密钥";

    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String encrypt(String plainText) {
        try {
            return encrypt(plainText, true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("MD5加密异常:", e);
            return null;
        }
    }

    /**
     * @param @param plainText
     * @param @param flag true为32位,false为16位
     * @throws UnsupportedEncodingException
     * @Title: encrypt
     * @Description: TODO(16位或32位密码)
     */
    public static String encrypt(String plainText, boolean flag) throws UnsupportedEncodingException {
        try {
            if (StringUtils.isEmpty(plainText)) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            String encrStr = byteArrayToHexString(md.digest(plainText.getBytes("UTF-8")));
            if (flag)
                return encrStr;
            else
                return encrStr.substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

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
        return encrypt(map, true);
    }

    /**
     * @param @param plainText
     * @param @param flag true为32位,false为16位
     * @throws UnsupportedEncodingException
     * @Title: encrypt
     * @Description: TODO(16位或32位密码)
     */
    public static String encrypt(Map<String, Object> map, boolean flag) {
        String param = null;
        map.remove("sign");
        map.remove("encrypt");
        String result = BeanUtil.mapOrderStr(map);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        param = encrypt(encrypt(result) + PRIVATE_KEY);
        if (flag) {
            return param;
        } else {
            param = param.substring(8, 24);
        }
        return param;
    }

    public static Map<String, Object> resultMap = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> mapFn(Map<String, Object> map) {
        for (String key : map.keySet()) {
            if (map.get(key) != null && map.get(key) != "" && (!key.equals("BTYPE") && !key.equals("SIGN"))) {
                if (key.equals("INPUT")) {
                    if (map.get(key) != null) {
                        mapFn((Map<String, Object>) map.get(key));
                    }
                } else {
                    resultMap.put(key, map.get(key));
                }
            }
        }
        return resultMap;
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

    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("id","1");
        treeMap.put("age","20");
        treeMap.put("claes","你好");
        treeMap.put("name","gyu");
        treeMap.put("createTime","2018-11-20");
        StringBuilder sb = new StringBuilder();
        Iterator iter = treeMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            // 获取key
            String key = (String)entry.getKey();
            // 获取value
            String value = (String)entry.getValue();
            sb.append(key + "=" + value + "&");
        }
        String aaa = sb.substring(0, sb.length() - 1);
        String param = MD5.encrypt(MD5.encrypt(aaa) + MD5.PRIVATE_KEY);
        System.out.println(param.equals("f89afe7421cedb8fe266856992f4cdf6"));
    }

}

