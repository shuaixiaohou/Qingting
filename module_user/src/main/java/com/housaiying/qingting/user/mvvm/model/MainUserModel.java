package com.housaiying.qingting.user.mvvm.model;

import android.app.Application;

import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.net.RxAdapter;
import com.housaiying.qingting.common.net.dto.GitHubDTO;

import io.reactivex.Observable;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/11 16:39
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class MainUserModel extends ZhumulangmaModel {
    public MainUserModel(Application application) {
        super(application);
    }

    public Observable<GitHubDTO> getGitHub() {
        return mNetManager.getCacheProvider().getGitHub(mNetManager.getUserService().getGitHub())
                .compose(RxAdapter.exceptionTransformer())
                .compose(RxAdapter.schedulersTransformer());
    }
}
