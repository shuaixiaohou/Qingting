package com.housaiying.qingting.common.mvvm.view.status;

import android.content.Context;
import android.view.View;

import com.housaiying.qingting.common.R;
import com.kingja.loadsir.callback.Callback;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/22 10:46
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class BlankStatus extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.common_layout_init;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);

    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        //不响应reload事件
        return true;
    }

}
