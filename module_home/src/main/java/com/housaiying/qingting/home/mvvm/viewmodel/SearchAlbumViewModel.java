package com.housaiying.qingting.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.QingTingModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 15:12
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class SearchAlbumViewModel extends BaseRefreshViewModel<QingTingModel, Album> {
    private SingleLiveEvent<List<Album>> mInitAlbumsEvent;

    private int curPage = 1;
    private String mKeyword;

    public SearchAlbumViewModel(@NonNull Application application, QingTingModel model) {
        super(application, model);
    }

    public void init() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchedAlbums(map)
                .subscribe(albumList -> {
                    if (CollectionUtils.isEmpty(albumList.getAlbums())) {
                        getShowEmptyViewEvent().call();
                        return;
                    }
                    curPage++;
                    getClearStatusEvent().call();
                    getInitAlbumsEvent().setValue(albumList.getAlbums());

                }, e -> {
                    getShowErrorViewEvent().call();
                    e.printStackTrace();
                });
    }

    private void getMoreAlbum() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchedAlbums(map)
                .subscribe(albumList -> {
                    if (!CollectionUtils.isEmpty(albumList.getAlbums())) {
                        curPage++;
                    }
                    getFinishLoadmoreEvent().setValue(albumList.getAlbums());
                }, e -> {
                    getFinishLoadmoreEvent().call();
                    e.printStackTrace();
                });
    }


    @Override
    public void onViewLoadmore() {
        getMoreAlbum();
    }


    public SingleLiveEvent<List<Album>> getInitAlbumsEvent() {
        return mInitAlbumsEvent = createLiveData(mInitAlbumsEvent);
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }
}
