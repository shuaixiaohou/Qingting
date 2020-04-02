package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.bean.PlayHistoryBean;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.home.R;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class RadioHistoryAdapter extends BaseQuickAdapter<PlayHistoryBean, BaseViewHolder> {
    public RadioHistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayHistoryBean item) {
        Glide.with(mContext).load(item.getSchedule().getRelatedProgram().getBackPicUrl()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, QingTingUtil.toWanYi(item.getSchedule().getRadioPlayCount()));
        helper.setText(R.id.tv_desc, "上次收听: " + item.getSchedule().getRelatedProgram().getProgramName());
        helper.setText(R.id.tv_radio_name, item.getSchedule().getRadioName());
    }
}
