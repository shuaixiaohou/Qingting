package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.ZhumulangmaUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class SearchTrackAdapter extends BaseQuickAdapter<Track, BaseViewHolder> {
    public SearchTrackAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Track item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_playcount, ZhumulangmaUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_album, item.getAlbum().getAlbumTitle());
        helper.setText(R.id.tv_duration, ZhumulangmaUtil.secondToTime(item.getDuration()));
    }
}
