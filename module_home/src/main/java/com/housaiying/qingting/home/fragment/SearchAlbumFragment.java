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
import com.housaiying.qingting.home.adapter.AlbumAdapter;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.SearchAlbumViewModel;
import com.kingja.loadsir.callback.Callback;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/13 15:12
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:搜索专辑
 */
public class SearchAlbumFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, SearchAlbumViewModel, Album> {


    private AlbumAdapter mAlbumAdapter;

    public SearchAlbumFragment() {

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
        mAlbumAdapter = new AlbumAdapter(R.layout.home_item_album_line);
        mAlbumAdapter.bindToRecyclerView(mBinding.recyclerview);

    }

    @Override
    public void initListener() {
        super.initListener();
        mAlbumAdapter.setOnItemClickListener((adapter, view, position) ->
                RouterUtil.navigateTo(mRouter.build(Constants.Router.Home.F_ALBUM_DETAIL)
                        .withLong(KeyCode.Home.ALBUMID, mAlbumAdapter.getItem(position).getId())));
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mAlbumAdapter);
    }

    @Override
    public void initData() {
        String keyword = getArguments().getString(KeyCode.Home.KEYWORD);
        mViewModel.setKeyword(keyword);
        mViewModel.init();
    }


    @Override
    public Class<SearchAlbumViewModel> onBindViewModel() {
        return SearchAlbumViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitAlbumsEvent().observe(this, albums -> mAlbumAdapter.setNewData(albums));
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
