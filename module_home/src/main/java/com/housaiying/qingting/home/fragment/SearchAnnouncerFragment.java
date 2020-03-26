package com.housaiying.qingting.home.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.databinding.CommonLayoutListBinding;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.mvvm.view.status.ListSkeleton;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.AnnouncerAdapter;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchAnnouncerViewModel;
import com.kingja.loadsir.callback.Callback;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 15:12
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:搜索主播
 */
public class SearchAnnouncerFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, SearchAnnouncerViewModel, Announcer> {

    private AnnouncerAdapter mAnnouncerAdapter;

    public SearchAnnouncerFragment() {

    }


    @Override
    protected int onBindLayout() {
        return R.layout.common_layout_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView.setBackground(null);

    }

    @Override
    protected void initView() {
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recyclerview.setHasFixedSize(true);
        mAnnouncerAdapter = new AnnouncerAdapter(R.layout.home_item_announcer);
        mAnnouncerAdapter.bindToRecyclerView(mBinding.recyclerview);
        mAnnouncerAdapter.setOnItemClickListener((adapter, view, position) ->
                RouterUtil.navigateTo(mRouter.build(Constants.Router.Home.F_ANNOUNCER_DETAIL)
                        .withLong(KeyCode.Home.ANNOUNCER_ID, mAnnouncerAdapter.getItem(position).getAnnouncerId())
                        .withString(KeyCode.Home.ANNOUNCER_NAME, mAnnouncerAdapter.getItem(position).getNickname())));
    }


    @Override
    public void initData() {
        String keyword = getArguments().getString(KeyCode.Home.KEYWORD);
        mViewModel.setKeyword(keyword);
        mViewModel.init();
    }


    @Override
    public Class<SearchAnnouncerViewModel> onBindViewModel() {
        return SearchAnnouncerViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitAnnouncersEvent().observe(this, announcers -> mAnnouncerAdapter.setNewData(announcers));
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mAnnouncerAdapter);
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
