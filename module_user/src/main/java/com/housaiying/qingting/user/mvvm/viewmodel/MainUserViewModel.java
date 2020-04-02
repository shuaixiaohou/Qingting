package com.housaiying.qingting.user.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.housaiying.qingting.user.mvvm.model.MainUserModel;
import com.ximalaya.ting.android.opensdk.model.user.XmBaseUserInfo;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:52
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class MainUserViewModel extends BaseRefreshViewModel<MainUserModel, Object> {
    private SingleLiveEvent<XmBaseUserInfo> mBaseUserInfoEvent;

    public MainUserViewModel(@NonNull Application application, MainUserModel model) {
        super(application, model);
    }

    public SingleLiveEvent<XmBaseUserInfo> getBaseUserInfoEvent() {
        return mBaseUserInfoEvent = createLiveData(mBaseUserInfoEvent);
    }
}
