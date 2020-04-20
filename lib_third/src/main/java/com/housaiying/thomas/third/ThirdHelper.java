package com.housaiying.thomas.third;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import me.yokeyword.fragmentation.Fragmentation;

public class ThirdHelper {
    private static final String TAG = "ThirdHelper";
    private static Application mApplication;
    private static volatile ThirdHelper instance;

    private ThirdHelper() {
    }

    public static ThirdHelper getInstance(Application application) {
        if (instance == null) {
            synchronized (ThirdHelper.class) {
                if (instance == null) {
                    mApplication = application;
                    instance = new ThirdHelper();
                }
            }
        }
        return instance;
    }

    public ThirdHelper initAgentWebX5() {
        QbSdk.initX5Environment(mApplication, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished() called");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d(TAG, "onViewInitFinished() called with: b = [" + b + "]");
            }
        });
        return this;
    }

    public ThirdHelper initUM() {
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(mApplication, UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxb2a0b79c29b9f928", "0edd9fa5ed6b82fae67d4ea14ac034ac");
        PlatformConfig.setSinaWeibo(Constants.SINA_ID, Constants.SINA_KEY, "http://sns.whalecloud.com");
        return this;
    }

    public ThirdHelper initRouter() {
        ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
        return this;
    }

    public ThirdHelper initUtils() {
        Utils.init(mApplication);
        ToastUtils.init(mApplication);
        ToastUtils.setView(R.layout.third_layout_toast);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        KeyboardUtils.clickBlankArea2HideSoftInput();
        return this;
    }

    public ThirdHelper initFragmentation(boolean isDebug) {
        // 建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(isDebug)
                .install();
        return this;
    }

    public ThirdHelper initSpeech() {
        SpeechUtility.createUtility(mApplication, SpeechConstant.APPID + "=" + Constants.SPEECH_ID);
        return this;
    }

}
