package com.housaiying.qingting.listen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.bean.FavoriteBean;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.listen.R;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class FavoriteAdapter extends BaseQuickAdapter<FavoriteBean, BaseViewHolder> {
    public FavoriteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean item) {
        Glide.with(mContext).load(item.getTrack().getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.getTrack().getTrackTitle());

        helper.setText(R.id.tv_album, item.getTrack().getAlbum().getAlbumTitle());
        helper.setText(R.id.tv_duration, QingTingUtil.secondToTime(item.getTrack().getDuration()));

        helper.addOnClickListener(R.id.ll_delete);
    }
}
