package com.platform.common.sysenum;

public enum MsgProductBean {

    ACCOUNT_INFO("accountInfo", "tag_account"),
    ACCOUNT_INFO_OTHER("accountInfo", "tag_account_other");

    private String beanName;
    private String tagName;

    MsgProductBean(String beanName, String tagName) {
        this.tagName = tagName;
        this.beanName = beanName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getBeanName() {
        return beanName;
    }

}
