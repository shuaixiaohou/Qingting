package com.housaiying.qingting.common.util;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.bean.NavigateBean;
import com.housaiying.qingting.common.event.ActivityEvent;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.mvvm.view.SupportActivity;
import com.housaiying.qingting.common.mvvm.view.SupportFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/24 15:25<br/>
 * Description:页面跳转
 */
public class RouterUtil {
    private static final String TAG = "RouterUtil";

    public static void navigateTo(String path) {
        navigateTo(ARouter.getInstance().build(path));
    }

    public static void navigateTo(Postcard postcard) {
        navigateTo(postcard, ISupportFragment.SINGLETASK);
    }

    public static void navigateTo(String path, int launchMode) {
        navigateTo(ARouter.getInstance().build(path), launchMode);
    }

    public static void navigateTo(Postcard postcard, int launchMode) {
        navigateTo(postcard, launchMode, null);
    }

    public static void navigateTo(String path, ExtraTransaction extraTransaction) {
        navigateTo(ARouter.getInstance().build(path), extraTransaction);
    }

    public static void navigateTo(Postcard postcard, ExtraTransaction extraTransaction) {
        navigateTo(postcard, ISupportFragment.SINGLETASK, extraTransaction);
    }

    public static void navigateTo(String path, int launchMode, ExtraTransaction extraTransaction) {
        navigateTo(ARouter.getInstance().build(path), launchMode, extraTransaction);
    }

    public static void navigateTo(Postcard postcard, int launchMode, ExtraTransaction extraTransaction) {
        Object navigation = postcard.navigation();
        NavigateBean navigateBean = new NavigateBean(postcard.getPath(), (SupportFragment) navigation);
        navigateBean.launchMode = launchMode;
        navigateBean.extraTransaction = extraTransaction;
        if (null != navigation) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.NAVIGATE, navigateBean));
        }
    }

    /**
     * 分发路由
     *
     * @param activity
     * @param navigateBean
     */
    public static void dispatcher(SupportActivity activity, NavigateBean navigateBean) {
        Objects.requireNonNull(navigateBean);
        Objects.requireNonNull(navigateBean.fragment);
        switch (navigateBean.path) {
            case Constants.Router.Home.F_PLAY_TRACK:
            case Constants.Router.Home.F_PLAY_RADIIO:
                activity.extraTransaction().setCustomAnimations(
                        com.housaiying.qingting.common.R.anim.push_bottom_in,
                        com.housaiying.qingting.common.R.anim.no_anim,
                        com.housaiying.qingting.common.R.anim.no_anim,
                        com.housaiying.qingting.common.R.anim.push_bottom_out).start(
                        navigateBean.fragment, ISupportFragment.SINGLETASK);
                break;
            default:
                if (navigateBean.extraTransaction != null) {
                    navigateBean.extraTransaction.start(navigateBean.fragment, navigateBean.launchMode);
                } else {
                    activity.start(navigateBean.fragment, navigateBean.launchMode);
                }
                break;
        }
    }
}
