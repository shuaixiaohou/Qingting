package com.housaiying.qingting.home.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.housaiying.qingting.common.bean.ProvinceBean;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.ProvinceAdapter;
import com.lxj.xpopup.impl.PartShadowPopupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/30 10:33
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:省市台省份选择弹窗
 */
public class RadioProvincePopup extends PartShadowPopupView implements BaseQuickAdapter.OnItemClickListener {
    private onSelectedListener mListener;

    private List<ProvinceBean> mProvinceBeans;
    private onPopupDismissingListener mDismissingListener;

    public RadioProvincePopup(@NonNull Context context) {
        super(context);
    }

    public RadioProvincePopup(@NonNull Context context, onSelectedListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.home_dialog_recyclerview;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        String s = ResourceUtils.readAssets2String("province.json");
        mProvinceBeans = new Gson().fromJson(s, new TypeToken<ArrayList<ProvinceBean>>() {
        }.getType());
        RecyclerView rvProvince = findViewById(R.id.recyclerview);
        rvProvince.setLayoutManager(new GridLayoutManager(getContext(), 5));
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(R.layout.home_item_rank_category, mProvinceBeans);
        rvProvince.setHasFixedSize(true);
        provinceAdapter.bindToRecyclerView(rvProvince);
        provinceAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        dismissWith(() -> {
            if (mListener != null) {
                mListener.onSelected(mProvinceBeans.get(position).getProvince_code(),
                        mProvinceBeans.get(position).getProvince_name());
            }
        });

    }

    public void setDismissingListener(onPopupDismissingListener dismissingListener) {
        mDismissingListener = dismissingListener;
    }

    @Override
    protected void doDismissAnimation() {
        super.doDismissAnimation();
        if (mDismissingListener != null)
            mDismissingListener.onDismissing();
    }

    public interface onSelectedListener {
        void onSelected(int province_code, String province_name);
    }

    public interface onPopupDismissingListener {

        void onDismissing();
    }
}
