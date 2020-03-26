package com.housaiying.qingting.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.ZhumulangmaUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * Created by housaiying
 * on 2020/3/12
 */
public class HotMusicAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {
    public HotMusicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        Glide.with(mContext).load(item.getCoverUrlLarge()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, ZhumulangmaUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_title, item.getAlbumTitle());
        helper.setText(R.id.tv_desc, item.getAlbumIntro());
    }
}
