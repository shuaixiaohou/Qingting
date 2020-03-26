package com.housaiying.qingting.home.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.databinding.CommonLayoutListBinding;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.mvvm.view.status.ListSkeleton;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.AnnouncerTrackAdapter;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.TrackListViewModel;
import com.kingja.loadsir.callback.Callback;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/11 11:40
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:声音列表
 */

@Route(path = Constants.Router.Home.F_TRACK_LIST)
public class TrackListFragment extends BaseRefreshMvvmFragment<CommonLayoutListBinding, TrackListViewModel, Track> implements
        BaseQuickAdapter.OnItemClickListener {

    @Autowired(name = KeyCode.Home.ANNOUNCER_ID)
    public long mAnnouncerId;
    @Autowired(name = KeyCode.Home.TITLE)
    public String mTitle;
    private AnnouncerTrackAdapter mAnnouncerTrackAdapter;

    @Override
    protected int onBindLayout() {
        return R.layout.common_layout_list;
    }

    @Override
    protected void initView() {
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recyclerview.setHasFixedSize(true);
        mAnnouncerTrackAdapter = new AnnouncerTrackAdapter(R.layout.home_item_announcer_track);
        mAnnouncerTrackAdapter.bindToRecyclerView(mBinding.recyclerview);
        setTitle(new String[]{mTitle});
    }

    @Override
    public void initListener() {
        super.initListener();
        mAnnouncerTrackAdapter.setOnItemClickListener(this);
        mBinding.refreshLayout.setOnLoadMoreListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mAnnouncerTrackAdapter);
    }

    @Override
    public void initData() {
        mViewModel.setAnnouncerId(mAnnouncerId);
        mViewModel.init();
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitTrackListEvent().observe(this, tracks -> mAnnouncerTrackAdapter.setNewData(tracks));
    }

    @Override
    protected boolean enableLazy() {
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Track track = mAnnouncerTrackAdapter.getItem(position);
        mViewModel.play(track.getAlbum().getAlbumId(), track.getDataId());
    }

    @Override
    public Class<TrackListViewModel> onBindViewModel() {
        return TrackListViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
