package com.platform.common.sign;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * @author jianghy
 * @Description: DES工具类
 * @date 2020/6/28 11:04
 */
public class DES {
    // 密钥
    private final static String secretKey = "123456789987654321";
    // 向量
    private final static String iv = "ggboy123";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * @param plainText 1
     * @return java.lang.String
     * @throws
     * @Description: DES加密
     * @author jianghy
     * @date 2020/6/28 13:24
     */
    public static String encode(String plainText) {
        Key deskey = null;
        DESedeKeySpec spec;
        try {
            spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            return Base64.encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param encryptText 1
     * @return java.lang.String
     * @throws
     * @Description: DES解密
     * @author jianghy
     * @date 2020/6/28 13:25
     */
    public static String decode(String encryptText) {
        try {

            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

            return new String(decryptData, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}

