package com.dsmh.common.enumeration;

public enum Gender {
    //男性
    MALE("M","男"),
    //女性
    FEMALE("F","女");

    private final String code;
    private final String name;

    Gender(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName(){
        return name;
    }
}
