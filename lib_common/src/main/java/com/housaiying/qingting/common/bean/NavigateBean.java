package com.housaiying.qingting.common.bean;

import com.housaiying.qingting.common.mvvm.view.SupportFragment;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/16 14:34
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class NavigateBean {
    public String path;
    public SupportFragment fragment;
    public ExtraTransaction extraTransaction;
    public @ISupportFragment.LaunchMode
    int launchMode = ISupportFragment.SINGLETASK;

    public NavigateBean(String path, SupportFragment fragment) {
        this.path = path;
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return path;
    }
}
