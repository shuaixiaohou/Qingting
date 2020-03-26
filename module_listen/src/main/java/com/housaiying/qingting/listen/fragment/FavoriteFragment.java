package com.housaiying.qingting.listen.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.bean.FavoriteBean;
import com.housaiying.qingting.common.databinding.CommonLayoutListBinding;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.listen.R;
import com.housaiying.qingting.listen.adapter.FavoriteAdapter;
import com.housaiying.qingting.listen.mvvm.ViewModelFactory;
import com.housaiying.qingting.listen.mvvm.viewmodel.FavoriteViewModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/16 8:45
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:我的喜欢
 */
@Route(path = Constants.Router.Listen.F_FAVORITE)
public class FavoriteFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, FavoriteViewModel, FavoriteBean> implements
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected int onBindLayout() {
        return R.layout.common_layout_list;
    }

    @Override
    protected void initView() {
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recyclerview.setHasFixedSize(true);
        mFavoriteAdapter = new FavoriteAdapter(R.layout.listen_item_favorite);
        mFavoriteAdapter.bindToRecyclerView(mBinding.recyclerview);
    }

    @Override
    public void initListener() {
        super.initListener();
        mFavoriteAdapter.setOnItemChildClickListener(this);
        mFavoriteAdapter.setOnItemClickListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mFavoriteAdapter);
    }


    @Override
    public void initData() {
        mViewModel.init();
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitFavoritesEvent().observe(this, favoriteBeans -> mFavoriteAdapter.setNewData(favoriteBeans));
    }


    @Override
    public String[] onBindBarTitleText() {
        return new String[]{"我喜欢的声音"};
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mViewModel.unlike(mFavoriteAdapter.getItem(position).getTrack());
        mFavoriteAdapter.remove(position);
        if (mFavoriteAdapter.getData().size() == 0) {
            showEmptyView();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Track track = mFavoriteAdapter.getItem(position).getTrack();
        mViewModel.play(track.getAlbum().getAlbumId(), track.getDataId());
    }

    @Override
    public Class<FavoriteViewModel> onBindViewModel() {
        return FavoriteViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

}
