package com.housaiying.qingting.listen.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.App;
import com.housaiying.qingting.common.mvvm.model.ZhumulangmaModel;
import com.housaiying.qingting.common.util.ZhumulangmaUtil;
import com.housaiying.qingting.listen.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class RecommendAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {

    private ZhumulangmaModel model = new ZhumulangmaModel(App.getInstance());

    public RecommendAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        Glide.with(mContext).load(item.getCoverUrlMiddle()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, ZhumulangmaUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_title, item.getAlbumTitle());
        helper.setText(R.id.tv_track_num, String.format(mContext.getResources().getString(R.string.ji),
                item.getIncludeTrackCount()));
        helper.setText(R.id.tv_desc, item.getLastUptrack().getTrackTitle());
        //是否订阅
        helper.setGone(R.id.ll_subscribe, !item.isCanDownload());
        helper.setGone(R.id.ll_unsubscribe, item.isCanDownload());

        helper.addOnClickListener(R.id.ll_subscribe);
        helper.addOnClickListener(R.id.ll_unsubscribe);
    }
}
