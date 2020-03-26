package com.housaiying.qingting.common.net.dto;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/30 17:43
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class ResponseDTO<T> {

    public T result;
    public String code;
    public String msg;

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "result=" + result +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
