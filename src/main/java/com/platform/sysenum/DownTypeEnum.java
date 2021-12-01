package com.platform.sysenum;

/**
 * @author jianghy
 * 接口返回码枚举类
 * @date 2020-4-17 13:23
 */
public enum DownTypeEnum {

    //插入
    UN_DOWN("0", "未下载完"),
    //修改
    DOWN("1", "已下载完");

    DownTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //根据码值获取名称
    public static String getName(String code) {
        for (DownTypeEnum val : values()) {
            if (val.getValue().equals(code)) {
                return val.desc;
            }
        }
        return null;
    }
}
