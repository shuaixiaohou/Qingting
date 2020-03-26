package com.housaiying.qingting.common.net;

import com.housaiying.qingting.common.bean.BingBean;
import com.housaiying.qingting.common.net.dto.GitHubDTO;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/29 8:55<br/>
 * Description:RxCache网络缓存
 */
public interface CacheProvider {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<GitHubDTO> getGitHub(Observable<GitHubDTO> observable);

    Observable<BingBean> getBing(Observable<BingBean> observable, EvictProvider evictProvider);
}
