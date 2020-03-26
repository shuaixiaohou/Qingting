package com.housaiying.qingting.common.net.exception;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:可以被拦截的异常类
 */
public class InterceptableException extends Exception {
    public static final String TOKEN_OUTTIME = "0004";
    public String code;
    public String message;

    public InterceptableException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public InterceptableException(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
    }

    @Override
    public String toString() {
        return "CustException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
