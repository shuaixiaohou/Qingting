package com.housaiying.qingting.common.net.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/12 11:16<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:公用Api
 */
public interface CommonService {
    /**
     * 从网络中获取ResponseBody
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> getCommonBody(@Url String url);
}
