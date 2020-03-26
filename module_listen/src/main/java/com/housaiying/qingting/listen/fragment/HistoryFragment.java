package com.housaiying.qingting.listen.fragment;

import android.app.AlertDialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.databinding.CommonLayoutListBinding;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.mvvm.view.status.ListSkeleton;
import com.housaiying.qingting.listen.R;
import com.housaiying.qingting.listen.adapter.HistoryAdapter;
import com.housaiying.qingting.listen.bean.PlayHistoryItem;
import com.housaiying.qingting.listen.mvvm.ViewModelFactory;
import com.housaiying.qingting.listen.mvvm.viewmodel.HistoryViewModel;
import com.kingja.loadsir.callback.Callback;

import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/16 8:44
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
@Route(path = Constants.Router.Listen.F_HISTORY)
public class HistoryFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, HistoryViewModel, PlayHistoryItem> implements
        BaseQuickAdapter.OnItemClickListener {

    private HistoryAdapter mHistoryAdapter;

    @Override
    protected int onBindLayout() {
        return R.layout.common_layout_list;
    }

    @Override
    protected void initView() {
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recyclerview.setHasFixedSize(true);
        mHistoryAdapter = new HistoryAdapter(null);
        mHistoryAdapter.bindToRecyclerView(mBinding.recyclerview);
    }

    @Override
    public void initListener() {
        super.initListener();
        mHistoryAdapter.setOnItemClickListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mHistoryAdapter);
    }

    @Override
    public void initData() {
        mViewModel.init();
    }

    @Override
    public String[] onBindBarTitleText() {
        return new String[]{"播放历史"};
    }


    @Override
    public Integer[] onBindBarRightIcon() {
        return new Integer[]{R.drawable.ic_listen_history_delete};
    }

    @Override
    public Class<HistoryViewModel> onBindViewModel() {
        return HistoryViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitHistorysEvent().observe(this, historyItems -> mHistoryAdapter.setNewData(historyItems));
    }

    @Override
    protected void onLoadMoreSucc(List<PlayHistoryItem> list) {
        //两页衔接处处理
        if (!CollectionUtils.isEmpty(mHistoryAdapter.getData()) && mViewModel.dateCovert(
                mHistoryAdapter.getItem(mHistoryAdapter.getData().size() - 1).data.getDatatime())
                .equals(list.get(0).header)) {
            list.remove(0);
        }
        mHistoryAdapter.addData(list);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PlayHistoryItem playHistoryItem = mHistoryAdapter.getItem(position);
        if (playHistoryItem.itemType != PlayHistoryItem.HEADER) {
            if (playHistoryItem.itemType == PlayHistoryItem.TRACK) {
                mViewModel.playRadio(playHistoryItem.data.getGroupId(),
                        playHistoryItem.data.getTrack().getDataId());
            } else {
                mViewModel.playRadio(String.valueOf(playHistoryItem.data.getGroupId()));
            }
        }
    }

    @Override
    public void onRight1Click(View v) {
        super.onRight1Click(v);
        new AlertDialog.Builder(mActivity)
                .setMessage("确定要清空播放历史吗?")
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("确定", (dialog, which) -> {
                    mViewModel.clear();
                    mHistoryAdapter.getData().clear();
                    showEmptyView();
                }).show();
    }

    @Override
    protected boolean enableLazy() {
        return false;
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
