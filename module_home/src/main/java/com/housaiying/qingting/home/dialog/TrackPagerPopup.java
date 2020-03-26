package com.housaiying.qingting.home.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.TrackPagerAdapter;
import com.lxj.xpopup.impl.PartShadowPopupView;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/30 10:33
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:专辑声音分页弹窗
 */
public class TrackPagerPopup extends PartShadowPopupView {

    private TrackPagerAdapter mPagerAdapter;
    private RecyclerView rvPager;

    private BaseQuickAdapter.OnItemClickListener mListener;
    private onPopupDismissingListener mDismissingListener;

    public TrackPagerPopup(@NonNull Context context) {
        super(context);
    }

    public TrackPagerPopup(@NonNull Context context, BaseQuickAdapter.OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        mPagerAdapter = new TrackPagerAdapter(R.layout.home_item_pager);
        return R.layout.home_dialog_recyclerview;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        rvPager = findViewById(R.id.recyclerview);
        rvPager.setLayoutManager(new GridLayoutManager(getContext(), 4));

        mPagerAdapter.bindToRecyclerView(rvPager);
        mPagerAdapter.setOnItemClickListener(mListener);
    }

    public void setDismissingListener(onPopupDismissingListener dismissingListener) {
        mDismissingListener = dismissingListener;
    }

    @Override
    protected void doDismissAnimation() {
        super.doDismissAnimation();
        if (mDismissingListener != null)
            mDismissingListener.onDismissing();
    }

    public TrackPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    public RecyclerView getRvPager() {
        return rvPager;
    }

    public interface onPopupDismissingListener {

        void onDismissing();
    }
}
