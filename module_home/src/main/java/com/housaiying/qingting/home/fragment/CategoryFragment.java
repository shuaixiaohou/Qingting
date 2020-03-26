package com.housaiying.qingting.home.fragment;


import com.housaiying.qingting.common.mvvm.view.BaseFragment;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.databinding.HomeFragmentCategoryBinding;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/14 13:41
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:分类
 */
public class CategoryFragment extends BaseFragment<HomeFragmentCategoryBinding> {


    public CategoryFragment() {

    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_category;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean enableLazy() {
        return true;
    }

    @Override
    public boolean enableSimplebar() {
        return false;
    }
}
