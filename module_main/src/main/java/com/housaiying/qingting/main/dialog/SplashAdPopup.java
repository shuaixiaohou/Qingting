package com.housaiying.qingting.main.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.db.DBManager;
import com.housaiying.qingting.common.net.RxAdapter;
import com.housaiying.qingting.main.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.impl.FullScreenPopupView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashAdPopup extends FullScreenPopupView implements View.OnClickListener {
    private Context mContext;
    private int mAdSecond = 5;

    public SplashAdPopup(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.main_dialog_splash_ad;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    public void onClick(View v) {
        DBManager.getInstance().getSPString(Constants.SP.AD_URL)
                .compose(RxAdapter.exceptionTransformer())
                .compose(RxAdapter.schedulersTransformer())
                .doOnSubscribe((Consumer<Disposable>) mContext)
                .subscribe(path -> {
                    Uri uri = Uri.parse(path);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }, Throwable::printStackTrace);
    }

    public static class AlphaAnimator extends PopupAnimator {
        @Override
        public void initAnimator() {
            targetView.setAlpha(1);
        }

        @Override
        public void animateShow() {
        }

        @Override
        public void animateDismiss() {
            targetView.animate().alpha(0).scaleX(1.5f).scaleY(1.5f).
                    setInterpolator(new LinearInterpolator()).setDuration(300).start();
        }
    }
}
