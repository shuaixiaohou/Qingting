package com.housaiying.qingting.main.dialog;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.housaiying.qingting.main.R;

import com.lxj.xpopup.impl.FullScreenPopupView;

public class SplashPopup extends FullScreenPopupView {
    private Context mContext;

    public SplashPopup(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.main_dialog_splash;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView ivAd = findViewById(R.id.iv_ad);
    }
}
