package com.housaiying.qingting.home.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class RankFreeAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {
    public RankFreeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, QingTingUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_title, item.getAlbumTitle());
        helper.setText(R.id.tv_track_num, String.format(mContext.getResources().getString(R.string.ji),
                item.getIncludeTrackCount()));
        helper.setText(R.id.tv_desc, item.getLastUptrack().getTrackTitle());

        TextView tvIndex = helper.getView(R.id.tv_index);
        if (helper.getLayoutPosition() == 0) {
            tvIndex.setTextColor(Color.parseColor("#ff0000"));
        } else if (helper.getLayoutPosition() == 1) {
            tvIndex.setTextColor(Color.parseColor("#ff9900"));
        } else if (helper.getLayoutPosition() == 2) {
            tvIndex.setTextColor(Color.parseColor("#4a86e8"));
        } else {
            tvIndex.setTextColor(Color.parseColor("#999999"));
        }
        tvIndex.setText(String.valueOf(helper.getLayoutPosition() + 1));
    }
}
