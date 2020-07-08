package com.platform.common.sign;

/**
 * @author jianghy
 * @Description: 签名枚举类
 * @date 2020/6/28 10:55
 */
public enum SginEnum {
    //0不需要签名，1使用MD5数据加密 2 使用SHA数据加密
    ANY(0), MD5(1), SHA1(2);
    private final int value;

    SginEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

