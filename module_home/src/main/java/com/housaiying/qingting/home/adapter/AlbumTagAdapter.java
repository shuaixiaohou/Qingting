package com.housaiying.qingting.home.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;

/**
 * Created by housaiying
 * on 2020/3/25
 */
public class AlbumTagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AlbumTagAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView view = helper.getView(R.id.tv_keyword);

        view.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        view.setBackgroundResource(R.drawable.shap_common_tag_first);

        helper.setText(R.id.tv_keyword, item);
    }
}
