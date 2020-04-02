package com.housaiying.qingting.listen.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.housaiying.qingting.common.mvvm.model.QingTingModel;
import com.housaiying.qingting.listen.mvvm.model.HistoryModel;
import com.housaiying.qingting.listen.mvvm.viewmodel.DownloadSortViewModel;
import com.housaiying.qingting.listen.mvvm.viewmodel.DownloadViewModel;
import com.housaiying.qingting.listen.mvvm.viewmodel.FavoriteViewModel;
import com.housaiying.qingting.listen.mvvm.viewmodel.HistoryViewModel;
import com.housaiying.qingting.listen.mvvm.viewmodel.SubscribeViewModel;

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
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel(mApplication, new HistoryModel(mApplication));
        } else if (modelClass.isAssignableFrom(DownloadViewModel.class)) {
            return (T) new DownloadViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(SubscribeViewModel.class)) {
            return (T) new SubscribeViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(FavoriteViewModel.class)) {
            return (T) new FavoriteViewModel(mApplication, new QingTingModel(mApplication));
        } else if (modelClass.isAssignableFrom(DownloadSortViewModel.class)) {
            return (T) new DownloadSortViewModel(mApplication, new QingTingModel(mApplication));
        }


        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}