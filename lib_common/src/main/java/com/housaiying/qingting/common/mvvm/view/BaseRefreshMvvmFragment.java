package com.housaiying.qingting.common.mvvm.view;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:自动处理刷新Fragment基类
 */
public abstract class BaseRefreshMvvmFragment<DB extends ViewDataBinding, VM extends BaseRefreshViewModel, T>
        extends BaseMvvmFragment<DB, VM>
        implements OnRefreshLoadMoreListener {

    private WrapRefresh mWrapRefresh;

    @CallSuper
    @Override
    public void initListener() {
        super.initListener();
        mWrapRefresh = onBindWrapRefresh();
        mWrapRefresh.refreshLayout.setOnRefreshLoadMoreListener(this);
    }


    protected abstract @NonNull
    WrapRefresh onBindWrapRefresh();

    @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        mViewModel.getFinishRefreshEvent().observe(this, (Observer<List<T>>) list -> {
            if (list == null) {
                mWrapRefresh.refreshLayout.finishRefresh(false);
                return;
            }
            if (list.size() == 0) {
                mWrapRefresh.refreshLayout.finishRefresh(true);
                return;
            }
            mWrapRefresh.refreshLayout.finishRefresh(true);
            onRefreshSucc(list);
        });
        mViewModel.getFinishLoadmoreEvent().observe(this, (Observer<List<T>>) list -> {
            if (list == null) {
                mWrapRefresh.refreshLayout.finishLoadMore(false);
                return;
            }
            if (list.size() == 0) {
                mWrapRefresh.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
            mWrapRefresh.refreshLayout.finishLoadMore(true);
            onLoadMoreSucc(list);
        });
    }

    protected void onRefreshSucc(List<T> list) {
        if (mWrapRefresh.quickAdapter != null) {
            mWrapRefresh.quickAdapter.setNewData(list);
        }
    }

    protected void onLoadMoreSucc(List<T> list) {
        if (mWrapRefresh.quickAdapter != null) {
            mWrapRefresh.quickAdapter.addData(list);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mViewModel.onViewLoadmore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.onViewRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mWrapRefresh)
            mWrapRefresh.refreshLayout.setOnRefreshLoadMoreListener(null);
    }

    protected class WrapRefresh {
        SmartRefreshLayout refreshLayout;
        BaseQuickAdapter<T, BaseViewHolder> quickAdapter;

        public WrapRefresh(@NonNull SmartRefreshLayout refreshLayout, BaseQuickAdapter<T, BaseViewHolder> quickAdapter) {
            this.refreshLayout = refreshLayout;
            this.quickAdapter = quickAdapter;
        }
    }
}
