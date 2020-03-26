package com.housaiying.qingting.home.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.housaiying.qingting.home.R;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/19 17:32
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class SpeechPopup extends CenterPopupView {

    public SpeechPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.home_dialog_speech;
    }

}
