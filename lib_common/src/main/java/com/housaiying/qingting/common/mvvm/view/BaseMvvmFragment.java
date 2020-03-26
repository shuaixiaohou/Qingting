package com.housaiying.qingting.common.mvvm.view;

import androidx.annotation.CallSuper;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.housaiying.qingting.common.mvvm.viewmodel.BaseViewModel;


/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:MvvmFragment基类
 */
public abstract class BaseMvvmFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<DB> {
    protected VM mViewModel;

    protected void initParam() {
        initViewModel();
        initBaseViewObservable();
        initViewObservable();
    }

    @CallSuper
    @Override
    protected void loadView() {
        super.loadView();
        //默认显示初始化视图
        showInitView();
    }

    protected void initViewModel() {
        mViewModel = createViewModel();
        getLifecycle().addObserver(mViewModel);
    }

    protected VM createViewModel() {
        return ViewModelProviders.of(this, onBindViewModelFactory()).get(onBindViewModel());
    }

    protected abstract Class<VM> onBindViewModel();

    protected abstract ViewModelProvider.Factory onBindViewModelFactory();

    protected abstract void initViewObservable();

    protected void initBaseViewObservable() {
        mViewModel.getShowInitViewEvent().observe(this, (Observer<Void>) show -> showInitView());
        mViewModel.getShowLoadingViewEvent().observe(this, (Observer<String>) this::showLoadingView);
        mViewModel.getShowEmptyViewEvent().observe(this, (Observer<Void>) show -> showEmptyView());
        mViewModel.getShowErrorViewEvent().observe(this, (Observer<Void>) show -> showErrorView());
        mViewModel.getFinishSelfEvent().observe(this, (Observer<Void>) v -> pop());
        mViewModel.getClearStatusEvent().observe(this, (Observer<Void>) v -> clearStatus());
    }
}
