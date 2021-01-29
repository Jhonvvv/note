package com.example.demo.model;


import java.io.Serializable;

public class ReturnT<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自定义返回码
     */
    private int code;


    /**
     * 自定义返回说明
     */
    private String msg;


    /**
     * 返回数据
     */
    private T data;


    /**
     * 成功返回，无数据
     *
     * @return 自定义的 ApiResponse 对象
     */
    public static ReturnT<Object> success(String prompt) {
        return new ReturnT<>(ApiCode.API_OK.code(), ApiCode.API_OK.getMsg(), prompt);
    }


    /**
     * 成功返回，有数据
     *
     * @param object 返回数据
     * @param <T>    返回数据类型
     * @return 自定义的 ApiResponse 对象
     */
    public static <T> ReturnT<T> success(T object) {
        return new ReturnT<>(ApiCode.API_OK.code(), ApiCode.API_OK.getMsg(), object);
    }


    /**
     * 失败返回
     *
     * @param apiCode 错误码
     * @return 自定义的 ApiResponse 对象
     */
    public static ReturnT<Object> fail(ApiCode apiCode,String prompt) {
        return new ReturnT<>(apiCode.code(), apiCode.getMsg(), prompt);
    }

    /**
     * 失败返回
     *
     * @param apiCode 错误码
     * @return 自定义的 ApiResponse 对象
     */
    public static ReturnT<Object> duplicateAccount(ApiCode apiCode) {
        return new ReturnT<>(apiCode.code(), apiCode.getMsg(), "");
    }

    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }


    public ReturnT(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
