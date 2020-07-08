package com.platform.common.sysenum;

/**
 * @author jianghy
 * 接口返回码枚举类
 * @date 2020-4-17 13:23
 */
public enum ResultTypeEnum {

    //接口验证通过
    SYS_SUCCESS("200", "接口验证通过"),
    //系统内部错误，请联系管理员
    SYS_ERROR("900", "系统内部错误，请联系管理员"),
    //token错误
    SYS_TOKEN_ERROR("800","token错误"),
    //请求格式错误
    SYS_REQUEST_ERROR("204","请求格式错误"),
    //无效签名
    SYS_SIGN_ERROR("700","无效签名"),

    //操作成功
    BIZ_SUCCESS("200000","操作成功"),
    //用户信息错误
    BIZ_USERINFO_ERROR("200700","用户信息错误"),
    //用户名错误
    BIZ_USERNAME_ERROR("200701","用户名错误"),
    //密码错误
    BIZ_PASSWORD_ERROR("200702","密码错误"),
    //验证码错误
    BIZ_CAPTCHA("200600","验证码错误");

    ResultTypeEnum(String value, String desc) {
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
        for (ResultTypeEnum val : values()) {
            if (val.getValue().equals(code)) {
                return val.desc;
            }
        }
        return null;
    }
}
