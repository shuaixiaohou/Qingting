package com.housaiying.qingting.main.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.bean.PlayHistoryBean;
import com.housaiying.qingting.common.db.PlayHistoryBeanDao;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseViewModel;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.main.mvvm.model.MainModel;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    private SingleLiveEvent<PlayHistoryBean> mHistoryEvent;
    private SingleLiveEvent<String> mCoverEvent;
    private SingleLiveEvent<Void> mShowAdEvent;

    public MainViewModel(@NonNull Application application, MainModel model) {
        super(application, model);
    }

    public void getLastSound() {
        mModel.listDesc(PlayHistoryBean.class, 0, 0, PlayHistoryBeanDao.Properties.Datatime, null)
                .subscribe(historyBeans -> {
                    if (!CollectionUtils.isEmpty(historyBeans)) {
                        getHistoryEvent().postValue(historyBeans.get(0));
                    }
                }, Throwable::printStackTrace);
    }

    public SingleLiveEvent<PlayHistoryBean> getHistoryEvent() {
        return mHistoryEvent = createLiveData(mHistoryEvent);
    }

    public void play(PlayHistoryBean historyBean) {
        switch (historyBean.getKind()) {
            case PlayableModel.KIND_TRACK:
                play(historyBean.getGroupId(), historyBean.getTrack().getDataId());
                break;
            case PlayableModel.KIND_SCHEDULE:
                playRadio(String.valueOf(historyBean.getGroupId()));
                break;
        }
    }

    public void play(long albumId, long trackId) {

        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, String.valueOf(albumId));
        map.put(DTransferConstants.TRACK_ID, String.valueOf(trackId));
        mModel.getLastPlayTracks(map)
                .doOnSubscribe(d -> getShowLoadingViewEvent().call())
                .doFinally(() -> getClearStatusEvent().call())
                .subscribe(trackList -> {
                    for (int i = 0; i < trackList.getTracks().size(); i++) {
                        if (trackList.getTracks().get(i).getDataId() == trackId) {
                            String coverUrlSmall = trackList.getTracks().get(i).getCoverUrlSmall();
                            getCoverEvent().postValue(TextUtils.isEmpty(coverUrlSmall)
                                    ? trackList.getTracks().get(i).getAlbum().getCoverUrlLarge() : coverUrlSmall);
                            XmPlayerManager.getInstance(getApplication()).playList(trackList, i);
                            break;
                        }
                    }
                    RouterUtil.navigateTo(Constants.Router.Home.F_PLAY_TRACK);
                }, Throwable::printStackTrace);
    }

    public void playRadio(String radioId) {
        mModel.getSchedulesSource(radioId)
                .doOnSubscribe(d -> getShowLoadingViewEvent().call())
                .doFinally(() -> getClearStatusEvent().call())
                .subscribe(schedules ->
                {
                    XmPlayerManager.getInstance(getApplication()).playSchedule(schedules, -1);
                    RouterUtil.navigateTo(Constants.Router.Home.F_PLAY_RADIIO);
                }, Throwable::printStackTrace);

    }

    public SingleLiveEvent<String> getCoverEvent() {
        return mCoverEvent = createLiveData(mCoverEvent);
    }

    public SingleLiveEvent<Void> getShowAdEvent() {
        return mShowAdEvent = createLiveData(mShowAdEvent);
    }
}
