package com.housaiying.qingting.home.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.downloadutil.DownloadState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by housaiying
 * on 2020/3/17
 */
public class DownloadTrackAdapter extends BaseQuickAdapter<Track, BaseViewHolder> {

    private List<Track> mSelectedTracks = new LinkedList<>();

    public DownloadTrackAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Track item) {
        helper.setText(R.id.tv_title, item.getTrackTitle());
        helper.setText(R.id.tv_duration, QingTingUtil.secondToTime(item.getDuration()));
        helper.setText(R.id.tv_size, QingTingUtil.byte2FitMemorySize(item.getDownloadSize()));
        helper.setText(R.id.tv_index, item.getOrderPositionInAlbum() + 1 + "");
        CheckBox checkBox = helper.getView(R.id.cb);
        checkBox.setChecked(mSelectedTracks.contains(item));
        if (XmDownloadManager.getInstance().getSingleTrackDownloadStatus(item.getDataId()) == DownloadState.NOADD) {
            checkBox.setEnabled(true);
        } else {
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
        }
    }

    public List<Track> getSelectedTracks() {
        return mSelectedTracks;
    }
}
