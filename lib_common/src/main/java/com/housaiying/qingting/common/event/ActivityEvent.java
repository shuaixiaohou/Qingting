package com.housaiying.qingting.common.event;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/18 13:58
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:Activity事件
 */
public class ActivityEvent extends BaseEvent {
    public ActivityEvent(int code) {
        super(code);
    }

    public ActivityEvent(int code, Object data) {
        super(code, data);
    }
}
