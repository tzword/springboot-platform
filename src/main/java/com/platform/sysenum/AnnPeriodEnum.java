package com.platform.sysenum;

/**
 * @author jianghy
 * 接口返回码枚举类
 * @date 2020-4-17 13:23
 */
public enum AnnPeriodEnum {

    ANNOUNCE_PERIOD_TITLE_Q1("1", "年第一季度报告"),
    ANNOUNCE_PERIOD_TITLE_Q2("2", "年半年度报告"),
    ANNOUNCE_PERIOD_TITLE_Q3("3", "年第三季度报告"),
    ANNOUNCE_PERIOD_TITLE_Q4("4", "年年度报告");


    AnnPeriodEnum(String value, String desc) {
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
        for (AnnPeriodEnum val : values()) {
            if (val.getValue().equals(code)) {
                return val.desc;
            }
        }
        return null;
    }
}
