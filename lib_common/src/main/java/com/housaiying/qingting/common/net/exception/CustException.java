package com.housaiying.qingting.common.net.exception;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:自定义异常类
 */
public class CustException extends Exception {
    public String code;
    public String message;

    public CustException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
