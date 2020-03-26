package com.housaiying.qingting.common.aop;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.housaiying.qingting.common.util.ToastUtil;
import com.thomas.okaspectj.PointHandler;
import com.ximalaya.ting.android.opensdk.datatrasfer.AccessTokenManager;

import org.aspectj.lang.ProceedingJoinPoint;

public class PointHelper implements PointHandler {
    private static final String TAG = "PointHelper";
    private Context mContext;

    public PointHelper(Context context) {
        mContext = context;
    }

    @Override
    public void handlePoint(Class clazz, ProceedingJoinPoint joinPoint) {
        Log.d(TAG, "handlePoint() called with: clazz = [" + clazz + "], joinPoint = [" + joinPoint + "]");
        try {
            if (clazz == NeedLogin.class) {
                if (AccessTokenManager.getInstanse().hasLogin()) {
                    joinPoint.proceed();
                } else {
                    ToastUtil.showToast("请先登陆");
                    LoginHelper.getInstance().login(ActivityUtils.getTopActivity());
                }
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
