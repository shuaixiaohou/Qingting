package com.housaiying.qingting.home.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.live.program.Program;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/6 16:58
 * <br/>Email: 1194959365@qq.com
 */
public class PlayRadioAdapter extends BaseQuickAdapter<Schedule, BaseViewHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm", Locale.getDefault());

    public PlayRadioAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Schedule item) {
        Program relatedProgram = item.getRelatedProgram();
        helper.setText(R.id.tv_title, relatedProgram.getProgramName());
        helper.setText(R.id.tv_time, item.getStartTime().substring(item.getStartTime().length() - 5) + "-"
                + item.getEndTime().substring(item.getEndTime().length() - 5));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < relatedProgram.getAnnouncerList().size(); i++) {
            sb.append(relatedProgram.getAnnouncerList().get(i).getNickName());
            if (i != relatedProgram.getAnnouncerList().size() - 1) {
                sb.append(",");
            }
        }
        helper.setText(R.id.tv_announcer_name, (TextUtils.isEmpty(sb.toString()) ? item.getRadioName() : sb.toString()));
        helper.setGone(R.id.tv_announcer_name, !TextUtils.isEmpty(sb.toString()) || !TextUtils.isEmpty(item.getRadioName()));

        try {
            long start = sdf.parse(item.getStartTime()).getTime();
            //直播
            helper.setGone(R.id.tv_zhibo,
                    BaseUtil.isInTime(item.getStartTime() + "-" + item.getEndTime()) == 0);
            helper.setGone(R.id.tv_huiting,
                    BaseUtil.isInTime(item.getStartTime() + "-" + item.getEndTime()) != 0);

            if (start > System.currentTimeMillis()) {
                helper.setGone(R.id.tv_huiting, false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
