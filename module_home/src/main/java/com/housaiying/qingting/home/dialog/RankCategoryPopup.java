package com.housaiying.qingting.home.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.RankCategotyAdapter;
import com.lxj.xpopup.impl.PartShadowPopupView;

import java.util.Arrays;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/30 10:33
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:排行榜分类弹窗
 */
public class RankCategoryPopup extends PartShadowPopupView implements BaseQuickAdapter.OnItemClickListener {
    private onSelectedListener mListener;

    private String[] mCLabels = {"热门", "音乐", "娱乐", "有声书"
            , "儿童", "3D体验馆", "资讯", "脱口秀"
            , "情感生活", "历史", "人文", "英语"
            , "小语种", "教育培训", "广播剧", "国学书院"
            , "电台", "商业财经", "IT科技", "健康养生"
            , "旅游", "汽车", "动漫游戏", "电影"};
    private String[] mCIds = {"0", "2", "4", "3"
            , "6", "29", "1", "28"
            , "10", "9", "39", "38"
            , "32", "13", "15", "40"
            , "17", "8", "18", "7"
            , "22", "21", "24", "23"};
    private onPopupDismissingListener mDismissingListener;

    public RankCategoryPopup(@NonNull Context context) {
        super(context);
    }

    public RankCategoryPopup(@NonNull Context context, onSelectedListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.home_dialog_recyclerview;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RecyclerView rvCategory = findViewById(R.id.recyclerview);
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(), 4));
        RankCategotyAdapter categotyAdapter = new RankCategotyAdapter(R.layout.home_item_rank_category,
                Arrays.asList(mCLabels));
        rvCategory.setHasFixedSize(true);
        categotyAdapter.bindToRecyclerView(rvCategory);
        categotyAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        dismissWith(() -> {
            if (mListener != null) {
                mListener.onSelected(mCIds[position], mCLabels[position]);
            }
        });

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

    public interface onSelectedListener {
        void onSelected(String category_id, String category_name);

    }

    public interface onPopupDismissingListener {

        void onDismissing();
    }
}
