package com.housaiying.qingting.common.mvvm.view;

import androidx.annotation.Nullable;

import com.housaiying.qingting.common.mvvm.view.status.BlankStatus;
import com.housaiying.qingting.common.mvvm.view.status.EmptyStatus;
import com.housaiying.qingting.common.mvvm.view.status.ErrorStatus;
import com.housaiying.qingting.common.mvvm.view.status.LoadingStatus;
import com.kingja.loadsir.callback.Callback;

import java.util.List;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/24 15:25<br/>
 * Description: Activity和Fragment公用方法
 */
public interface BaseView {

    /**
     * 提供状态布局
     *
     * @return
     */
    default Callback getInitStatus() {
        return new BlankStatus();
    }

    default Callback getLoadingStatus() {
        return new LoadingStatus();
    }

    default Callback getErrorStatus() {
        return new ErrorStatus();
    }

    default Callback getEmptyStatus() {
        return new EmptyStatus();
    }

    /**
     * 提供额外状态布局
     *
     * @return
     */
    default @Nullable
    List<Callback> getExtraStatus() {
        return null;
    }

}
