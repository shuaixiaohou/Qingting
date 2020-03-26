package com.housaiying.qingting.home.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.common.databinding.CommonLayoutListBinding;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.mvvm.view.status.ListSkeleton;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.RadioAdapter;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchRadioViewModel;
import com.kingja.loadsir.callback.Callback;
import com.ximalaya.ting.android.opensdk.model.live.radio.Radio;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/18 13:58
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:搜索电台
 */
public class SearchRadioFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, SearchRadioViewModel, Radio> implements
        BaseQuickAdapter.OnItemClickListener {

    private RadioAdapter mRadioAdapter;

    public SearchRadioFragment() {

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
        mRadioAdapter = new RadioAdapter(R.layout.home_item_radio_line);
        mRadioAdapter.bindToRecyclerView(mBinding.recyclerview);
        mRadioAdapter.setOnItemClickListener(this);
    }


    @Override
    public void initData() {
        String keyword = getArguments().getString(KeyCode.Home.KEYWORD);
        mViewModel.setKeyword(keyword);
        mViewModel.init();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mViewModel.playRadio(mRadioAdapter.getItem(position));
    }

    @Override
    public Class<SearchRadioViewModel> onBindViewModel() {
        return SearchRadioViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitRadiosEvent().observe(this, radioList -> mRadioAdapter.setNewData(radioList));
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
        return new WrapRefresh(mBinding.refreshLayout, mRadioAdapter);
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
