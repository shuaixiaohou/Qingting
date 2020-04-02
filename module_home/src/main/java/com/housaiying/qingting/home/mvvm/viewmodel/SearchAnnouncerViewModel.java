package com.housaiying.qingting.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.QingTingModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 15:12
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class SearchAnnouncerViewModel extends BaseRefreshViewModel<QingTingModel, Announcer> {
    private SingleLiveEvent<List<Announcer>> mInitAnnouncersEvent;

    private int curPage = 1;
    private String mKeyword;

    public SearchAnnouncerViewModel(@NonNull Application application, QingTingModel model) {
        super(application, model);
    }

    public void init() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchAnnouncers(map)
                .subscribe(announcerList -> {
                    if (CollectionUtils.isEmpty(announcerList.getAnnouncerList())) {
                        getShowEmptyViewEvent().call();
                        return;
                    }
                    curPage++;
                    getClearStatusEvent().call();
                    getInitAnnouncersEvent().setValue(announcerList.getAnnouncerList());

                }, e -> {
                    getShowErrorViewEvent().call();
                    e.printStackTrace();
                });
    }

    private void getMoreAnnouncers() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchAnnouncers(map)
                .subscribe(announcerList -> {
                    if (!CollectionUtils.isEmpty(announcerList.getAnnouncerList())) {
                        curPage++;
                    }
                    getFinishLoadmoreEvent().setValue(announcerList.getAnnouncerList());
                }, e -> {
                    getFinishLoadmoreEvent().call();
                    e.printStackTrace();
                });
    }


    @Override
    public void onViewLoadmore() {
        getMoreAnnouncers();
    }

    public SingleLiveEvent<List<Announcer>> getInitAnnouncersEvent() {
        return mInitAnnouncersEvent = createLiveData(mInitAnnouncersEvent);
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }
}
