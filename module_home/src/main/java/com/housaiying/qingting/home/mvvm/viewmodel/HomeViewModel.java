package com.housaiying.qingting.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.housaiying.qingting.common.event.SingleLiveEvent;
import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseViewModel;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 11:10
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class HomeViewModel extends BaseViewModel<ZhumulangmaModel> {
    private SingleLiveEvent<List<HotWord>> mHotWordsEvent;

    public HomeViewModel(@NonNull Application application, ZhumulangmaModel model) {
        super(application, model);

    }

    public void getHotWords() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.TOP, String.valueOf(20));
        mModel.getHotWords(map)
                .subscribe(hotWordList -> getHotWordsEvent().setValue(hotWordList.getHotWordList()), e -> {
                    e.printStackTrace();
                });
    }


    public SingleLiveEvent<List<HotWord>> getHotWordsEvent() {
        return mHotWordsEvent = createLiveData(mHotWordsEvent);
    }

}
