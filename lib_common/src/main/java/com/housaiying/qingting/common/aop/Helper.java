package com.housaiying.qingting.common.aop;

import com.ximalaya.ting.android.opensdk.auth.handler.XmlySsoHandler;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/15 14:57<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:帮助类
 */
public class Helper {

    private static volatile Helper instance;
    private XmlySsoHandler mSsoHandler;

    private Helper() {
    }

    public static Helper getInstance() {
        if (instance == null) {
            synchronized (Helper.class) {
                if (instance == null) {
                    instance = new Helper();
                }
            }
        }
        return instance;
    }

    public XmlySsoHandler getSsoHandler() {
        return mSsoHandler;
    }

    public void out() {
        System.exit(0);
    }
}
