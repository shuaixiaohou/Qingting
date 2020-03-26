package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.column.Column;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class HotTopicAdapter extends BaseQuickAdapter<Column, BaseViewHolder> {
    public HotTopicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Column item) {
        Glide.with(mContext).load(item.getCoverUrlSmall()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, item.getColumnFootNote());
        helper.setText(R.id.tv_desc, item.getColumnSubTitle());
        helper.setText(R.id.tv_title, item.getColumnTitle());
        helper.setImageResource(R.id.iv_type, item.getColumnContentType() == 1 ? R.drawable.ic_common_zhuanji
                : R.drawable.ic_common_ji);
    }
}
