package com.housaiying.qingting.home.fragment;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.event.FragmentEvent;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.extra.GlideImageLoader;
import com.housaiying.qingting.common.mvvm.view.BaseRefreshMvvmFragment;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.FineAdapter;
import com.housaiying.qingting.home.databinding.HomeFragmentFineBinding;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.FineViewModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/14 13:41
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:精品
 */
public class FineFragment extends BaseRefreshMvvmFragment<HomeFragmentFineBinding, FineViewModel, Album> implements
        View.OnClickListener, OnBannerListener {

    private FineAdapter mDailyAdapter;
    private FineAdapter mBookAdapter;
    private FineAdapter mClassroomAdapter;


    public FineFragment() {

    }


    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_fine;
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected void initView() {
        initBanner();
        initDaily();
        initBook();
        initClassRoom();
    }

    @Override
    public void initListener() {
        super.initListener();
        mBinding.banner.setOnBannerListener(this);
        mBinding.dailyRefresh.setOnClickListener(this);
        mBinding.bookRefresh.setOnClickListener(this);
        mBinding.classroomRefresh.setOnClickListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, null);
    }


    @Override
    public void initData() {
        mViewModel.init();
        String notice = "本页面为付费内容,目前仅提供浏览功能,暂时不可操作!";
        mBinding.marqueeView.setContent(notice);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getBannerV2Event().observe(this, bannerV2s -> {
            List<String> images = new ArrayList<>();
            for (BannerV2 bannerV2 : bannerV2s) {
                images.add(bannerV2.getBannerUrl());
            }
            mBinding.banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        });
        mViewModel.getDailysEvent().observe(this, albums -> mDailyAdapter.setNewData(albums));
        mViewModel.getBooksEvent().observe(this, albums -> mBookAdapter.setNewData(albums));
        mViewModel.getClassRoomsEvent().observe(this, albums -> mClassroomAdapter.setNewData(albums));
    }

    private void initBanner() {
        mBinding.banner.setIndicatorGravity(BannerConfig.RIGHT);
        mBinding.banner.setDelayTime(3000);
    }

    private void initDaily() {

        mDailyAdapter = new FineAdapter(R.layout.home_item_fine);
        mBinding.rvDaily.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvDaily.setHasFixedSize(true);
        mDailyAdapter.bindToRecyclerView(mBinding.rvDaily);
    }

    private void initBook() {
        mBookAdapter = new FineAdapter(R.layout.home_item_fine);
        mBinding.rvBook.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvBook.setHasFixedSize(true);
        mBookAdapter.bindToRecyclerView(mBinding.rvBook);

    }

    private void initClassRoom() {
        mClassroomAdapter = new FineAdapter(R.layout.home_item_fine);
        mBinding.rvClassroom.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvClassroom.setHasFixedSize(true);
        mClassroomAdapter.bindToRecyclerView(mBinding.rvClassroom);
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }

    @Override
    public Class<FineViewModel> onBindViewModel() {
        return FineViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.daily_refresh == id) {
            mViewModel.getDailyList();
        } else if (R.id.book_refresh == id) {
            mViewModel.getBookList();
        } else if (R.id.classroom_refresh == id) {
            mViewModel.getClassRoomList();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (null != mBinding) {
            mBinding.banner.startAutoPlay();
        }
        if (null != mBinding) {
            mBinding.marqueeView.continueRoll();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (null != mBinding) {
            mBinding.banner.stopAutoPlay();
        }
        if (null != mBinding) {
            mBinding.marqueeView.stopRoll();
        }
    }

    @Override
    public void onEvent(FragmentEvent event) {
        super.onEvent(event);
        switch (event.getCode()) {
            case EventCode.Home.TAB_REFRESH:
                if (isSupportVisible() && mBaseLoadService.getCurrentCallback() != getInitStatus().getClass()) {
                    mBinding.nsv.scrollTo(0, 0);
                    mBinding.refreshLayout.autoRefresh();
                }
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        BannerV2 bannerV2 = mViewModel.getBannerV2Event().getValue().get(position);
        switch (bannerV2.getBannerContentType()) {
            case 2:
                RouterUtil.navigateTo(mRouter.build(Constants.Router.Home.F_ALBUM_DETAIL)
                        .withLong(KeyCode.Home.ALBUMID, bannerV2.getAlbumId()));
                break;
            case 3:
                mViewModel.play(bannerV2.getTrackId());
                break;
            case 1:
                RouterUtil.navigateTo(mRouter.build(Constants.Router.Home.F_ANNOUNCER_DETAIL)
                        .withLong(KeyCode.Home.ANNOUNCER_ID, bannerV2.getBannerUid()));
                break;
        }
    }
}
