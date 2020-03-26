package com.housaiying.qingting.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.bean.ProvinceBean;
import com.housaiying.qingting.home.R;

import java.util.List;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class ProvinceAdapter extends BaseQuickAdapter<ProvinceBean, BaseViewHolder> {

    public ProvinceAdapter(int layoutResId, @Nullable List<ProvinceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvinceBean item) {
        helper.setText(R.id.tv_label, item.getProvince_name());
    }
}
