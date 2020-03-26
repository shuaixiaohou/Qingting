package com.housaiying.qingting.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.bean.SearchHistoryBean;
import com.housaiying.qingting.home.R;

/**
 * Created by housaiying
 * on 2020/3/25
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    public SearchHistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {
        helper.setText(R.id.tv_keyword, item.getKeyword());
    }
}
