package com.housaiying.qingting.discover.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.event.FragmentEvent;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.mvvm.view.BaseFragment;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.discover.R;
import com.housaiying.qingting.discover.databinding.DiscoverFragmentMainBinding;

@Route(path = Constants.Router.Discover.F_MAIN)
public class MainDiscoverFragment extends BaseFragment<DiscoverFragmentMainBinding> implements View.OnClickListener {

    @Override
    protected int onBindLayout() {
        return R.layout.discover_fragment_main;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void initListener() {
        super.initListener();
        mBinding.clFfjp.setOnClickListener(this);
        mBinding.clQmld.setOnClickListener(this);
        mBinding.clTyq.setOnClickListener(this);
        mBinding.clDkzb.setOnClickListener(this);
        mBinding.clWd.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean enableLazy() {
        return false;
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
        return new String[]{"最新疫情"};
    }

    @Override
    public void onClick(View v) {
        RouterUtil.navigateTo(mRouter.build(Constants.Router.Discover.F_WEB)
                .withString(KeyCode.Discover.PATH, v.getTag().toString()));
    }

    @Override
    public void onEvent(FragmentEvent event) {
        super.onEvent(event);
        switch (event.getCode()) {
            case EventCode.Discover.TAB_REFRESH:

                break;
        }
    }
}
