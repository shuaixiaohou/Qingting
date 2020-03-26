package com.housaiying.qingting.listen.adapter;

import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.ZhumulangmaUtil;
import com.housaiying.qingting.listen.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 14:51
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class DownloadDeleteAdapter extends BaseQuickAdapter<Track, BaseViewHolder> {
    public DownloadDeleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Track item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_size, ZhumulangmaUtil.byte2FitMemorySize(item.getDownloadedSize()));

        helper.setText(R.id.tv_duration, ZhumulangmaUtil.secondToTime(item.getDuration()));
        CheckBox checkBox = helper.getView(R.id.cb);
        checkBox.setChecked(item.isPaid());
    }
}
