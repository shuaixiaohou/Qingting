package com.housaiying.qingting.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.QingTingModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.housaiying.qingting.common.util.RouterUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;
import com.ximalaya.ting.android.opensdk.model.track.LastPlayTrackList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackListV2;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class AnnouncerListViewModel extends BaseRefreshViewModel<QingTingModel, Announcer> {

    private SingleLiveEvent<List<Announcer>> mInitAnnouncersEvent;
    private int curPage = 1;
    private long mCategoryId;

    public AnnouncerListViewModel(@NonNull Application application, QingTingModel model) {
        super(application, model);
    }

    @Override
    public void onViewLoadmore() {
        getMoreAnnouncers();
    }

    public void init() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.VCATEGORY_ID, String.valueOf(mCategoryId));
        map.put(DTransferConstants.CALC_DIMENSION, "1");
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getAnnouncerList(map)
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
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.VCATEGORY_ID, String.valueOf(mCategoryId));
        map.put(DTransferConstants.CALC_DIMENSION, "1");
        map.put(DTransferConstants.PAGE, String.valueOf(curPage));
        mModel.getAnnouncerList(map)
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

    public void play(long trackId) {

        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ID, String.valueOf(trackId));
        mModel.searchTrackV2(map)
                .flatMap((Function<SearchTrackListV2, ObservableSource<LastPlayTrackList>>)
                        searchTrackListV2 -> {
                            Map<String, String> map1 = new HashMap<>();
                            map1.put(DTransferConstants.ALBUM_ID, String.valueOf(
                                    searchTrackListV2.getTracks().get(0).getAlbum().getAlbumId()));
                            map1.put(DTransferConstants.TRACK_ID, String.valueOf(trackId));
                            return mModel.getLastPlayTracks(map1);
                        })
                .doOnSubscribe(d -> getShowLoadingViewEvent().call())
                .doFinally(() -> getClearStatusEvent().call())
                .subscribe(trackList -> {
                    for (int i = 0; i < trackList.getTracks().size(); i++) {
                        if (trackList.getTracks().get(i).getDataId() == trackId) {
                            XmPlayerManager.getInstance(getApplication()).playList(trackList, i);
                            break;
                        }
                    }
                    RouterUtil.navigateTo(Constants.Router.Home.F_PLAY_TRACK);
                }, Throwable::printStackTrace);
    }

    public void setCategoryId(long categoryId) {
        this.mCategoryId = categoryId;
    }

    public SingleLiveEvent<List<Announcer>> getInitAnnouncersEvent() {
        return mInitAnnouncersEvent = createLiveData(mInitAnnouncersEvent);
    }
}
