package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.live.radio.Radio;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class HotRadioAdapter extends BaseQuickAdapter<Radio, BaseViewHolder> {
    public HotRadioAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Radio item) {
        Glide.with(mContext).load(item.getCoverUrlLarge()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, QingTingUtil.toWanYi(item.getRadioPlayCount()));
        helper.setText(R.id.tv_desc, item.getRadioDesc());
        helper.setText(R.id.tv_title, item.getProgramName());
        helper.setText(R.id.tv_author, item.getRadioName());

    }
}
