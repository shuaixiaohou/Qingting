package com.housaiying.qingting.listen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
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
public class DownloadSortAdapter extends BaseItemDraggableAdapter<Track, BaseViewHolder> {
    public DownloadSortAdapter(int layoutResId) {
        super(layoutResId, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, Track item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_size, ZhumulangmaUtil.byte2FitMemorySize(item.getDownloadedSize()));

        helper.setText(R.id.tv_album, item.getAlbum().getAlbumTitle());
        helper.setText(R.id.tv_duration, ZhumulangmaUtil.secondToTime(item.getDuration()));
        //历史播放进度
        if (item.getSource() == 0) {
            helper.setText(R.id.tv_hasplay, "");
        } else {
            helper.setText(R.id.tv_hasplay, mContext.getString(R.string.hasplay, item.getSource()));
        }
    }
}
