package com.housaiying.qingting.listen.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.housaiying.qingting.common.bean.PlayHistoryBean;
import com.housaiying.qingting.common.db.PlayHistoryBeanDao;
import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseViewModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.downloadutil.ComparatorUtil;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/1 9:20<br/>
 * Description:
 */
public class DownloadSortViewModel extends BaseViewModel<ZhumulangmaModel> {

    private SingleLiveEvent<List<Track>> mTracksEvent;

    public DownloadSortViewModel(@NonNull Application application, ZhumulangmaModel model) {
        super(application, model);
    }

    public void getDownloadTracks(long albumId) {
        List<Track> tracks;
        if (albumId == 0) {
            tracks = XmDownloadManager.getInstance().getDownloadTracks(true);
        } else {
            tracks = XmDownloadManager.getInstance().getDownloadTrackInAlbum(albumId, true);
        }
        Collections.sort(tracks, ComparatorUtil.comparatorByUserSort(true));
        Completable.fromObservable(Observable.fromIterable(tracks)
                .flatMap((Function<Track, ObservableSource<List<PlayHistoryBean>>>) track -> {
                    track.setSource(0);
                    return mModel.list(PlayHistoryBean.class, PlayHistoryBeanDao.Properties.SoundId.eq(track.getDataId()),
                            PlayHistoryBeanDao.Properties.Kind.eq(track.getKind()));
                })
                .observeOn(Schedulers.io())
                .doOnNext(historyBeans -> {
                    if (!CollectionUtils.isEmpty(historyBeans)) {
                        CollectionUtils.find(tracks, item ->
                                item.getDataId() == historyBeans.get(0).getSoundId())
                                .setSource(historyBeans.get(0).getPercent());
                    }
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> getClearStatusEvent().call())
                .subscribe(() -> getTracksEvent().setValue(tracks), Throwable::printStackTrace);
    }

    public SingleLiveEvent<List<Track>> getTracksEvent() {
        return mTracksEvent = createLiveData(mTracksEvent);
    }
}
