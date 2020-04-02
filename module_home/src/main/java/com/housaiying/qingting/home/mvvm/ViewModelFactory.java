package com.housaiying.qingting.home.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.housaiying.qingting.common.mvvm.model.QingTingModel;
import com.housaiying.qingting.home.mvvm.model.RadioModel;
import com.housaiying.qingting.home.mvvm.viewmodel.AlbumDetailViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.AlbumListViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.AnnouncerDetailViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.AnnouncerListViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.AnnouncerViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.BatchDownloadViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.FineViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.HomeViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.HotViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.PlayRadioViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.PlayTrackViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.RadioListViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.RadioViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.RankViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchAlbumViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchAnnouncerViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchRadioViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchTrackViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchViewModel;
import com.housaiying.qingting.home.mvvm.viewmodel.TrackListViewModel;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/30 9:30
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;
    private final Application mApplication;

    private ViewModelFactory(Application application) {
        this.mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HotViewModel.class)) {
            return (T) new HotViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(FineViewModel.class)) {
            return (T) new FineViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(RadioViewModel.class)) {
            return (T) new RadioViewModel(mApplication, new RadioModel(mApplication));
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(SearchRadioViewModel.class)) {
            return (T) new SearchRadioViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(RankViewModel.class)) {
            return (T) new RankViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(AlbumListViewModel.class)) {
            return (T) new AlbumListViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(AlbumDetailViewModel.class)) {
            return (T) new AlbumDetailViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(AnnouncerViewModel.class)) {
            return (T) new AnnouncerViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(PlayTrackViewModel.class)) {
            return (T) new PlayTrackViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(PlayRadioViewModel.class)) {
            return (T) new PlayRadioViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(RadioListViewModel.class)) {
            return (T) new RadioListViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(AnnouncerDetailViewModel.class)) {
            return (T) new AnnouncerDetailViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(TrackListViewModel.class)) {
            return (T) new TrackListViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(BatchDownloadViewModel.class)) {
            return (T) new BatchDownloadViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(AnnouncerListViewModel.class)) {
            return (T) new AnnouncerListViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(SearchAlbumViewModel.class)) {
            return (T) new SearchAlbumViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(SearchTrackViewModel.class)) {
            return (T) new SearchTrackViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(SearchAnnouncerViewModel.class)) {
            return (T) new SearchAnnouncerViewModel(mApplication, new QingTingModel(mApplication));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}