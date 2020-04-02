package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class AnnouncerTrackAdapter extends BaseQuickAdapter<Track, BaseViewHolder> {
    public AnnouncerTrackAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Track item) {

        Glide.with(mContext).load(item.getCoverUrlSmall()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_playcount, QingTingUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_duration, QingTingUtil.secondToTime(item.getDuration()));
        helper.setText(R.id.tv_create_time, TimeUtils.millis2String(item.getCreatedAt(), new SimpleDateFormat("yyyy-MM-dd")));

    }
}
