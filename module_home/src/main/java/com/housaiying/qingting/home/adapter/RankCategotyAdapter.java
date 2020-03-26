package com.housaiying.qingting.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;

import java.util.List;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class RankCategotyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RankCategotyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_label, item);
    }
}
