package com.housaiying.qingting.common.event;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/18 13:58
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:Fragment事件
 */
public class FragmentEvent extends BaseEvent {
    public FragmentEvent(int code) {
        super(code);
    }

    public FragmentEvent(int code, Object o) {
        super(code, o);
    }
}
