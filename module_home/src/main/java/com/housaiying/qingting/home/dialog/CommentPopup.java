package com.housaiying.qingting.home.dialog;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.housaiying.qingting.home.R;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/24 10:22
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class CommentPopup extends BottomPopupView implements View.OnClickListener {
    public CommentPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.home_dialog_comment;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.bt_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
