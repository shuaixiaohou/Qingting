package com.housaiying.qingting.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.housaiying.qingting.common.util.RouterUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 15:12
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class SearchTrackViewModel extends BaseRefreshViewModel<ZhumulangmaModel, Track> {
    private SingleLiveEvent<List<Track>> mInitTracksEvent;

    private int curPage = 1;
    private String mKeyword;

    public SearchTrackViewModel(@NonNull Application application, ZhumulangmaModel model) {
        super(application, model);
    }


    public void init() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchedTracks(map)
                .subscribe(trackList -> {
                    if (CollectionUtils.isEmpty(trackList.getTracks())) {
                        getShowEmptyViewEvent().call();
                        return;
                    }
                    curPage++;
                    getClearStatusEvent().call();
                    getInitTracksEvent().setValue(trackList.getTracks());

                }, e -> {
                    getShowErrorViewEvent().call();
                    e.printStackTrace();
                });
    }

    private void getMoreTracks() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, mKeyword);
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getSearchedTracks(map)
                .subscribe(trackList -> {
                    if (!CollectionUtils.isEmpty(trackList.getTracks())) {
                        curPage++;
                    }
                    getFinishLoadmoreEvent().setValue(trackList.getTracks());
                }, e -> {
                    getFinishLoadmoreEvent().call();
                    e.printStackTrace();
                });
    }

    @Override
    public void onViewLoadmore() {
        getMoreTracks();
    }

    public void play(String albumId, Track track) {

        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumId);
        map.put(DTransferConstants.TRACK_ID, String.valueOf(track.getDataId()));
        mModel.getLastPlayTracks(map)
                .subscribe(trackList -> {
                    XmPlayerManager.getInstance(getApplication()).playList(trackList,
                            trackList.getTracks().indexOf(track));
                    RouterUtil.navigateTo(Constants.Router.Home.F_PLAY_TRACK);
                }, Throwable::printStackTrace);
    }


    public SingleLiveEvent<List<Track>> getInitTracksEvent() {
        return mInitTracksEvent = createLiveData(mInitTracksEvent);
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }
}
