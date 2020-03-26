package com.housaiying.qingting.main.mvvm.model;

import android.app.Application;

import com.housaiying.qingting.common.bean.BingBean;
import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.net.RxAdapter;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;

public class MainModel extends ZhumulangmaModel {


    public MainModel(Application application) {
        super(application);
    }

    public Observable<BingBean> getBing(String format, String n) {

        return mNetManager.getCacheProvider().getBing(mNetManager.getCommonService().getBing(format, n), new EvictProvider(true))
                .compose(RxAdapter.exceptionTransformer())
                .compose(RxAdapter.schedulersTransformer());
    }

}
