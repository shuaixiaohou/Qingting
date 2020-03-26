package com.housaiying.qingting.listen.fragment;


import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.adapter.TFragmentPagerAdapter;
import com.housaiying.qingting.common.adapter.TabNavigatorAdapter;
import com.housaiying.qingting.common.mvvm.view.BaseFragment;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.listen.R;
import com.housaiying.qingting.listen.databinding.ListenFragmentMainBinding;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:我听
 */
@Route(path = Constants.Router.Listen.F_MAIN)
public class MainListenFragment extends BaseFragment<ListenFragmentMainBinding> implements View.OnClickListener {

    public MainListenFragment() {
    }

    @Override
    protected int onBindLayout() {
        return R.layout.listen_fragment_main;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void initView() {
        String[] tabs = {"我的订阅", "推荐订阅"};
        List<Fragment> pages = new ArrayList<>();
        pages.add(new SubscribeFragment());
        pages.add(new RecommendFragment());

        TFragmentPagerAdapter adapter = new TFragmentPagerAdapter(
                getChildFragmentManager(), pages);
        mBinding.viewpager.setOffscreenPageLimit(2);
        mBinding.viewpager.setAdapter(adapter);
        final CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new TabNavigatorAdapter(Arrays.asList(tabs), mBinding.viewpager, 60));
        mBinding.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mBinding.magicIndicator, mBinding.viewpager);
    }

    @Override
    public void initListener() {
        super.initListener();
        mBinding.llDownload.setOnClickListener(this);
        mBinding.llHistory.setOnClickListener(this);
        mBinding.llFavorite.setOnClickListener(this);
        mBinding.llPurchased.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean enableLazy() {
        return false;
    }

    @Override
    public SimpleBarStyle onBindBarLeftStyle() {
        return SimpleBarStyle.LEFT_ICON;
    }

    @Override
    public SimpleBarStyle onBindBarRightStyle() {
        return SimpleBarStyle.RIGHT_ICON;
    }

    @Override
    public Integer[] onBindBarRightIcon() {
        return new Integer[]{R.drawable.ic_common_search};
    }

    @Override
    public String[] onBindBarTitleText() {
        return new String[]{"我听"};
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.ll_download == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_DOWNLOAD);
        } else if (R.id.ll_history == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_HISTORY);
        } else if (R.id.ll_favorite == id) {
            RouterUtil.navigateTo(Constants.Router.Listen.F_FAVORITE);
        }
    }

    @Override
    public void onLeftIconClick(View v) {
        super.onLeftIconClick(v);
        RouterUtil.navigateTo(Constants.Router.User.F_MESSAGE);
    }

    @Override
    public void onRight1Click(View v) {
        super.onRight1Click(v);
        RouterUtil.navigateTo(Constants.Router.Home.F_SEARCH);
    }
}
