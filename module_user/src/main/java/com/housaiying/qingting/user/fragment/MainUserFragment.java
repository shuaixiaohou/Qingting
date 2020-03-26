package com.housaiying.qingting.user.fragment;

import android.Manifest;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.aop.LoginHelper;
import com.housaiying.qingting.common.event.ActivityEvent;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.event.FragmentEvent;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.extra.GlideApp;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.net.dto.GitHubDTO;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.common.util.ToastUtil;
import com.housaiying.qingting.common.util.ZhumulangmaUtil;
import com.housaiying.qingting.user.R;
import com.housaiying.qingting.user.databinding.UserFragmentMainBinding;
import com.housaiying.qingting.user.mvvm.ViewModelFactory;
import com.housaiying.qingting.user.mvvm.viewmodel.MainUserViewModel;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ximalaya.ting.android.opensdk.datatrasfer.AccessTokenManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/14 13:41
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:我的
 */
@Route(path = Constants.Router.User.F_MAIN)
public class MainUserFragment extends BaseRefreshMvvmFragment<UserFragmentMainBinding, MainUserViewModel, Object> implements View.OnClickListener {
    private GitHubDTO mGitHubDTO;

    @Override
    protected int onBindLayout() {
        return R.layout.user_fragment_main;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void loadView() {
        super.loadView();
        clearStatus();
    }

    @Override
    protected void initView() {
        initBar();

    }

    private void initBar() {
        TextView tvTitle = mBinding.ctbWhite.getCenterCustomView().findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("我的");
    }

    @Override
    public void initListener() {
        super.initListener();
        mBinding.nsv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (nestedScrollView, i, scrollY, i2, i3) -> {
                    mBinding.flParallax.setTranslationY(-scrollY);
                    mBinding.ctbWhite.setAlpha(ZhumulangmaUtil.visibleByScroll(SizeUtils.px2dp(scrollY), 0, 100));
                    mBinding.ctbTrans.setAlpha(ZhumulangmaUtil.unvisibleByScroll(SizeUtils.px2dp(scrollY), 0, 100));
                });
        mBinding.refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent,
                                       int offset, int headerHeight, int maxDragHeight) {
                mBinding.ctbTrans.setAlpha(1 - (float) offset / mBinding.ctbTrans.getHeight());

                mBinding.ivParallax.setScaleX((float) (1 + percent * 0.2));
                mBinding.ivParallax.setScaleY((float) (1 + percent * 0.2));

                mBinding.flParallax.setTranslationY(offset);
            }
        });
        mBinding.llDownload.setOnClickListener(this);
        mBinding.llHistory.setOnClickListener(this);
        mBinding.llFavorit.setOnClickListener(this);
        mBinding.ivUser.setOnClickListener(this);
        mBinding.clFxzq.setOnClickListener(this);
        mBinding.clSys.setOnClickListener(this);
        mBinding.clWxhd.setOnClickListener(this);
        mBinding.clJcgx.setOnClickListener(this);
        mBinding.clGy.setOnClickListener(this);
        mBinding.ivAvatar.setOnClickListener(this);
        mBinding.clZx.setOnClickListener(this);
        mBinding.tvNickname.setOnClickListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, null);
    }

    @Override
    public void initData() {
        mViewModel.init();
    }

    @Override
    protected void initViewObservable() {
        mViewModel.getGitHubEvent().observe(this, gitHubDTO -> {
            mGitHubDTO = gitHubDTO;
            mBinding.tvStar.setText(convertNum(gitHubDTO.getStargazers_count()));
            mBinding.tvFork.setText(convertNum(gitHubDTO.getForks_count()));
                  ((TextView) mView.findViewById(R.id.tv_desc)).setText(gitHubDTO.getDescription());
        });

        mViewModel.getBaseUserInfoEvent().observe(this, xmBaseUserInfo -> {
            GlideApp.with(MainUserFragment.this)
                    .load(xmBaseUserInfo.getAvatarUrl())
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(mBinding.ivAvatar);
            mBinding.tvNickname.setText(xmBaseUserInfo.getNickName());
            mBinding.tvVip.setVisibility(xmBaseUserInfo.isVip() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }

    @Override
    protected boolean enableLazy() {
        return false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.ll_download == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_DOWNLOAD);
        } else if (R.id.ll_history == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_HISTORY);
        } else if (R.id.ll_favorit == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_FAVORITE);
        } else if (id == R.id.cl_fxzq) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.SHARE));
        } else if (id == R.id.cl_sys) {
            new RxPermissions(this).requestEach(new String[]{Manifest.permission.CAMERA})
                    .subscribe(permission -> {
                        if (permission.granted) {
                            RouterUtil.navigateTo(Constants.Router.Home.F_SCAN);
                        } else {
                            ToastUtil.showToast("请允许应用使用相机权限");
                        }
                    });
        } else if (id == R.id.cl_wxhd) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_FAVORITE);
        } else if (id == R.id.cl_jcgx) {
            ToastUtil.showToast("已是最新版本");
        } else if (id == R.id.cl_gy || id == R.id.iv_user) {
            RouterUtil.navigateTo(mRouter.build(Constants.Router.Discover.F_WEB)
                    .withString(KeyCode.Discover.PATH, "https://github.com/shuaixiaohou"));
        } else if (id == R.id.cl_zx) {
            new AlertDialog.Builder(mActivity)
                    .setMessage("您确定要注销登录吗?")
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("确定", (dialog, which) -> LoginHelper.getInstance().logout()).show();
        } else if (id == R.id.tv_nickname || id == R.id.iv_avatar) {
            if (!AccessTokenManager.getInstanse().hasLogin()) {
                LoginHelper.getInstance().login(mActivity);
            }
        }
    }

    @Override
    protected Class<MainUserViewModel> onBindViewModel() {
        return MainUserViewModel.class;
    }

    @Override
    protected ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    private String convertNum(int num) {
        if (num < 1000) {
            return String.valueOf(num);
        }
        String dy1000 = String.valueOf(num / 1000);
        String xy1000 = String.valueOf(num % 1000 / 100);

        return dy1000 + "." + xy1000 + "k";
    }

    @Override
    public void onEvent(FragmentEvent event) {
        super.onEvent(event);
        switch (event.getCode()) {
            case EventCode.User.TAB_REFRESH:

                if (isSupportVisible() && mBaseLoadService.getCurrentCallback() != getInitStatus().getClass()) {
                    mBinding.nsv.scrollTo(0, 0);
                    mBinding.refreshLayout.autoRefresh();
                }
                break;
            case EventCode.Main.LOGINSUCC:
                mViewModel.init();
                break;
            case EventCode.Main.LOGOUTSUCC:
                Glide.with(MainUserFragment.this).load(R.drawable.ic_user_avatar).into(mBinding.ivAvatar);
                mBinding.tvNickname.setText("未登陆");
                mBinding.tvVip.setVisibility(View.GONE);
                break;
        }
    }

}
