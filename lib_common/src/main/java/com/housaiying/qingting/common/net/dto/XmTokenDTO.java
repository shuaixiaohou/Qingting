package com.housaiying.qingting.common.net.dto;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/24 9:44<br/>
 * Description:
 */
public class XmTokenDTO {

    /**
     * access_token : 3fa85c876c975cdd9b98c722e58fcbda
     * expires_in : 7200
     * refresh_token : 1f71b2e733ac1c0aff25d361c5d163c0
     * uid : 176778452
     * device_id : 853d7e246b272d99
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int uid;
    private String device_id;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
