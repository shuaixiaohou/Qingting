package com.housaiying.qingting.common.mvvm.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.housaiying.qingting.common.R;
import com.housaiying.qingting.common.bean.NavigateBean;
import com.housaiying.qingting.common.event.ActivityEvent;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.mvvm.view.status.LoadingStatus;
import com.housaiying.qingting.common.util.RouterUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * Author: housaiying
 * <br/>Date: 2020/3/10 8:23
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:Activity基类,为了减少布局层级,主要用于添加应用内悬浮窗,其他界面请添加到根fragment中
 */
public abstract class BaseActivity extends SupportActivity implements BaseView, Consumer<Disposable> {
    protected static final String TAG = BaseActivity.class.getSimpleName();
    //Disposable容器
    protected CompositeDisposable mDisposables = new CompositeDisposable();
    //状态页管理
    protected LoadService mBaseLoadService;
    //公用Handler
    protected Handler mHandler = new Handler();
    protected ARouter mRouter = ARouter.getInstance();
    //用于延时显示loading状态,避免一闪而过
    private Handler mLoadingHandler = new Handler();

    protected @LayoutRes
    int onBindLayout() {
        return View.NO_ID;
    }

    protected void initParam() {
    }

    public abstract void initView();

    public void initListener() {
    }

    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //减少布局层级
        if (onBindLayout() != View.NO_ID)
            setContentView(onBindLayout());
        EventBus.getDefault().register(this);
        mRouter.inject(this);
        initCommonView();
        initParam();
        initView();
        initListener();
        initData();
    }

    @Override
    public void accept(Disposable disposable) throws Exception {
        mDisposables.add(disposable);
    }


    /**
     * 初始化基本布局
     */
    private void initCommonView() {

        LoadSir.Builder builder = new LoadSir.Builder()
                .addCallback(getInitStatus())
                .addCallback(getEmptyStatus())
                .addCallback(getErrorStatus())
                .addCallback(getLoadingStatus())
                .setDefaultCallback(getInitStatus().getClass());
        if (!CollectionUtils.isEmpty(getExtraStatus())) {
            for (Callback callback : getExtraStatus()) {
                builder.addCallback(callback);
            }
        }
        mBaseLoadService = builder.build().register(findViewById(android.R.id.content), (Callback.OnReloadListener) BaseActivity.this::onReload);
        mBaseLoadService.showSuccess();
    }

    /**
     * 显示初始化状态页
     */
    public void showInitView() {
        clearStatus();
        mBaseLoadService.showCallback(getInitStatus().getClass());
    }

    /**
     * 显示出错状态页
     */
    public void showErrorView() {
        clearStatus();
        mBaseLoadService.showCallback(getErrorStatus().getClass());
    }

    /**
     * 显示空数据状态页
     */
    public void showEmptyView() {
        clearStatus();
        mBaseLoadService.showCallback(getEmptyStatus().getClass());

    }

    /**
     * 显示loading状态页
     *
     * @param tip 为null时不带提示文本
     */
    public void showLoadingView(String tip) {
        mLoadingHandler.removeCallbacksAndMessages(null);
        mBaseLoadService.showSuccess();
        mBaseLoadService.setCallBack(getLoadingStatus().getClass(), (context, view) -> {
            TextView tvTip = view.findViewById(R.id.tv_tip);
            if (tvTip == null) {
                throw new IllegalStateException(getLoadingStatus().getClass() + "必须带有显示提示文本的TextView,且id为R.id.tv_tip");
            }
            if (tip == null) {
                tvTip.setVisibility(View.GONE);
            } else {
                tvTip.setVisibility(View.VISIBLE);
                tvTip.setText(tip);
            }
        });
        //延时100毫秒显示,避免闪屏
        mLoadingHandler.postDelayed(() -> mBaseLoadService.showCallback(getLoadingStatus().getClass()), 300);
    }

    /**
     * 清除所有状态页
     */
    public void clearStatus() {
        mLoadingHandler.removeCallbacksAndMessages(null);
        mBaseLoadService.showSuccess();
    }

    /**
     * 点击状态页默认执行事件
     */
    protected void onReload(View v) {
        clearStatus();
        initData();
    }

    @CallSuper
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventSticky(ActivityEvent event) {
    }

    @CallSuper
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActivityEvent event) {
        //路由事件
        if (event.getCode() == EventCode.Main.NAVIGATE)
            RouterUtil.dispatcher(this, (NavigateBean) event.getData());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onBackPressedSupport() {
        //如果正在显示loading,则清除
        if (mBaseLoadService.getCurrentCallback() == LoadingStatus.class) {
            //通知ViewModel取消正在运行的工作
            ((LifecycleRegistry) getLifecycle()).handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            clearStatus();
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        KeyboardUtils.fixSoftInputLeaks(this);
        EventBus.getDefault().unregister(this);
        mDisposables.clear();
    }

}
