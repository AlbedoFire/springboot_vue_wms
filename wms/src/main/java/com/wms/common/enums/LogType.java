package com.wms.common.enums;

public enum LogType {
    LOGIN("登录"),
    REGISTER("注册"),
    ;
    private String value;

    public String getValue() {
        return value;
    }

    LogType(String value) {
        this.value = value;
    }
}
