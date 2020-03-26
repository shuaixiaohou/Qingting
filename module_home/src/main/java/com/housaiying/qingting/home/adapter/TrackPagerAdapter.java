package com.housaiying.qingting.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/28 15:55
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class TrackPagerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TrackPagerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_page, item);
    }
}
