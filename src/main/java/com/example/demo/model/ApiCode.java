package com.example.demo.model;

public enum ApiCode {
    /**
     * 通用成功
     */
    API_noRegion(400,"error"),//失败

    //账号重复
    API_repeat(202,"duplicateAccount"),
    API_OK(200, "ok"),

    ;



    private final int code;

    private final String msg;


    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int code() {
        return this.code;
    }


    public String getMsg() {
        return this.msg;
    }

}
