package com.housaiying.qingting.home.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.housaiying.qingting.home.R;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;

/**
 * Created by housaiying
 * on 2020/3/25
 */
public class SearchHotAdapter extends BaseQuickAdapter<HotWord, BaseViewHolder> {

    public SearchHotAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotWord item) {
        TextView view = helper.getView(R.id.tv_keyword);
        if (helper.getLayoutPosition() == 0) {
            view.setTextColor(Color.parseColor("#ff0000"));
            view.setBackgroundResource(R.drawable.shap_common_tag_first);
        } else if (helper.getLayoutPosition() == 1) {
            view.setTextColor(Color.parseColor("#ff9900"));
            view.setBackgroundResource(R.drawable.shap_common_tag_second);
        } else if (helper.getLayoutPosition() == 2) {
            view.setTextColor(Color.parseColor("#4a86e8"));
            view.setBackgroundResource(R.drawable.shap_common_tag_third);
        } else {
            view.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
            view.setBackgroundResource(R.drawable.shap_common_tag_normal);
        }
        helper.setText(R.id.tv_keyword, item.getSearchword());
    }
}
