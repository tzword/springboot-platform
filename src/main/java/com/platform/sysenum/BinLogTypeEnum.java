package com.platform.sysenum;

/**
 * @author jianghy
 * 接口返回码枚举类
 * @date 2020-4-17 13:23
 */
public enum BinLogTypeEnum {

    //插入
    INSERT("01", "INSERT"),
    //修改
    UPDATE("02", "UPDATE"),
    //删除
    DELETE("03","DELETE"),
    //创建
    CREATE("04","CREATE");

    BinLogTypeEnum(String value, String desc) {
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
        for (BinLogTypeEnum val : values()) {
            if (val.getValue().equals(code)) {
                return val.desc;
            }
        }
        return null;
    }
}
