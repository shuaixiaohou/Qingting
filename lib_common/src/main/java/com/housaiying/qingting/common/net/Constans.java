package com.housaiying.qingting.common.net;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/12 11:16<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:Constans
 */
public interface Constans {
    /**
     * Token
     */
    String TOKEN_KEY = "token";

    /**
     * 在线
     */
    int NET_ONLINE = 0;
    /**
     * 离线
     */
    int NET_OFFLINE = 1;

    String HOST_KEY = "HOST";
    /**
     * 主机1
     */
    String HOST1_VALUE = "Local";
    /**
     * 主机2
     */
    String HOST2_VALUE = "Other";
    /**
     * 第三方api
     */
    String REDIRECT_URL = "http://api.ximalaya.com/openapi-collector-app/get_access_token";
    /**
     * 测试环境
     */
    String OFFLINE_HOST1 = "https://cn.bing.com/";
    String OFFLINE_HOST2 = "http://192.168.31.105:8767/";
    /**
     * 正式环境
     */
    String ONLINE_HOST1 = "https://cn.bing.com/";
    String ONLINE_HOST2 = "http://192.168.31.105:8767/";
}
