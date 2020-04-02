package com.housaiying.qingting.listen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.bean.SubscribeBean;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.listen.R;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class SubscribeAdapter extends BaseQuickAdapter<SubscribeBean, BaseViewHolder> {
    public SubscribeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubscribeBean item) {

        Glide.with(mContext).load(item.getAlbum().getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, QingTingUtil.toWanYi(item.getAlbum().getPlayCount()));
        helper.setText(R.id.tv_title, item.getAlbum().getAlbumTitle());
        helper.setText(R.id.tv_desc, item.getAlbum().getAlbumIntro());
        helper.addOnClickListener(R.id.iv_more);
        helper.addOnClickListener(R.id.iv_play);

    }
}
