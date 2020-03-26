package com.housaiying.qingting.home.fragment;


import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.housaiying.qingting.common.mvvm.view.BaseMvvmFragment;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.SearchHistoryAdapter;
import com.housaiying.qingting.home.adapter.SearchHotAdapter;
import com.housaiying.qingting.home.databinding.HomeFragmentSearchHistoryBinding;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchViewModel;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:搜索下历史页
 */
public class SearchHistoryFragment extends BaseMvvmFragment<HomeFragmentSearchHistoryBinding, SearchViewModel>
        implements View.OnClickListener {

    private SearchHistoryAdapter mHistoryAdapter;
    private SearchHotAdapter mHotAdapter;
    private onSearchListener mSearchListener;

    public SearchHistoryFragment() {
    }

    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_search_history;
    }


    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void initView() {
        mBinding.tvClear.setOnClickListener(this);

        mBinding.rvHistory.setLayoutManager(new com.library.flowlayout.FlowLayoutManager());
        mBinding.rvHistory.setHasFixedSize(true);

        mHistoryAdapter = new SearchHistoryAdapter(R.layout.common_item_tag);
        mHistoryAdapter.bindToRecyclerView(mBinding.rvHistory);
        mHistoryAdapter.setOnItemClickListener((adapter, view1, position) -> {
            if (mSearchListener != null)
                mSearchListener.onSearch(mHistoryAdapter.getItem(position).getKeyword());
        });

        mBinding.rvHot.setLayoutManager(new com.library.flowlayout.FlowLayoutManager());
        mBinding.rvHot.setHasFixedSize(true);

        mHotAdapter = new SearchHotAdapter(R.layout.common_item_tag);
        mHotAdapter.bindToRecyclerView(mBinding.rvHot);
        mHotAdapter.setOnItemClickListener((adapter, view12, position) -> {
            if (mSearchListener != null)
                mSearchListener.onSearch(mHotAdapter.getItem(position).getSearchword());
        });
    }


    public void refreshHistory() {
        mViewModel.refreshHistory();
    }

    @Override
    public void initData() {
        mViewModel.getHistory();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.tv_clear == id) {
            mViewModel.clearHistory();
            mHistoryAdapter.setNewData(null);
        }
    }

    public void setSearchListener(onSearchListener searchListener) {
        mSearchListener = searchListener;
    }

    @Override
    public Class<SearchViewModel> onBindViewModel() {
        return SearchViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getHotWordsEvent().observe(this, hotWords -> mHotAdapter.setNewData(hotWords));
        mViewModel.getHistorySingleLiveEvent().observe(this, historyBeanList ->
                mHistoryAdapter.setNewData(historyBeanList));
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface onSearchListener {

        void onSearch(String keyword);
    }
}
