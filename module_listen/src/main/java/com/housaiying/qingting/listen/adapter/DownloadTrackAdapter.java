package com.housaiying.qingting.listen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.listen.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class DownloadTrackAdapter extends BaseQuickAdapter<Track, BaseViewHolder> {
    public DownloadTrackAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Track item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_size, QingTingUtil.byte2FitMemorySize(item.getDownloadedSize()));

        helper.setText(R.id.tv_album, item.getAlbum().getAlbumTitle());
        helper.setText(R.id.tv_duration, QingTingUtil.secondToTime(item.getDuration()));
        //历史播放进度
        if (item.getSource() == 0) {
            helper.setText(R.id.tv_hasplay, "");
        } else {
            helper.setText(R.id.tv_hasplay, mContext.getString(R.string.hasplay, item.getSource()));
        }
        helper.addOnClickListener(R.id.ll_delete);
    }
}
