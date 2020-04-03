package com.housaiying.qingting.discover.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.ActivityEvent;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.mvvm.view.BaseFragment;
import com.housaiying.qingting.discover.R;
import com.housaiying.qingting.discover.databinding.DiscoverFragmentWebBinding;
import com.just.agentwebX5.AgentWebX5;
import com.just.agentwebX5.ChromeClientCallbackManager;
import com.just.agentwebX5.DefaultWebClient;
import com.just.agentwebX5.DownLoadResultListener;
import com.just.agentwebX5.PermissionInterceptor;
import com.just.agentwebX5.WebDefaultSettingsManager;
import com.just.agentwebX5.WebSettings;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/25 15:44
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
@Route(path = Constants.Router.Discover.F_WEB)
public class WebFragment extends BaseFragment<DiscoverFragmentWebBinding> {
    @Autowired(name = KeyCode.Discover.PATH)
    public String mPath;
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            return false;
        }
    };
    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = (view, title) -> setTitle(new String[]{title});
    //AgentWeb 在触发某些敏感的 Action 时候会回调该方法， 比如定位触发 。
//例如 https//:www.baidu.com 该 Url 需要定位权限， 返回false ，如果版本大于等于23 ， agentWeb 会动态申请权限 ，true 该Url对应页面请求定位失败。
//该方法是每次都会优先触发的 ， 开发者可以做一些敏感权限拦截 。
    protected PermissionInterceptor mPermissionInterceptor = (url, permissions, action) -> {
        return false;
    };
    protected DownLoadResultListener mDownLoadResultListener = new DownLoadResultListener() {
        @Override
        public void success(String path) {
        }

        @Override
        public void error(String path, String resUrl, String cause, Throwable e) {
        }
    };
    private AgentWebX5 mAgentWeb;
    private ImageView ivClose;
    protected com.tencent.smtt.sdk.WebViewClient mWebViewClient = new com.tencent.smtt.sdk.WebViewClient() {
        int index = 1;
        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?vid=XODEzMjU1MTI4&refer=&tuid=&ua=Mozilla%2F5.0%20(Linux%3B%20Android%207.0%3B%20SM-G9300%20Build%2FNRD90M%3B%20wv)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Version%2F4.0%20Chrome%2F58.0.3029.83%20Mobile%20Safari%2F537.36&source=exclusive-pageload&cookieid=14971464739049EJXvh|Z6i1re#Intent;scheme=youku;package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://"))
                return true;
            else if (url.startsWith("youku"))
                return true;
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            timer.put(url, System.currentTimeMillis());
            if (url.equals(mPath)) {
                ivClose.setVisibility(View.GONE);
            } else {
                ivClose.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    private AlertDialog mAlertDialog;

    @Override
    protected int onBindLayout() {
        return R.layout.discover_fragment_web;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void initView() {
        ivClose = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.iv_left);
        ivClose.setImageResource(R.drawable.ic_common_web_close);
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void initListener() {
        super.initListener();
        ivClose.setOnClickListener(v -> showDialog());
    }

    @Override
    public void initData() {

        mAgentWeb = AgentWebX5.with(this)
                .setAgentWebParent(mBinding.flContainer, new FrameLayout.LayoutParams(-1, -1))
                .setIndicatorColorWithHeight(getResources().getColor(R.color.colorPrimary), 1)
                .setWebSettings(getSettings())
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setReceivedTitleCallback(mCallback)
                .setPermissionInterceptor(mPermissionInterceptor)
                .setNotifyIcon(R.mipmap.download)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownScheme()
                .openParallelDownload()
                .setSecurityType(AgentWebX5.SecurityType.strict)
                .addDownLoadResultListener(mDownLoadResultListener)
                .createAgentWeb()
                .ready()
//                .go(mPath);
                .go("https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_pc_1");
    }

    public WebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(mActivity)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", (dialog, which) -> {
                        if (mAlertDialog != null) {
                            mAlertDialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", (dialog, which) -> {

                        if (mAlertDialog != null) {
                            mAlertDialog.dismiss();
                        }
                        pop();
                    }).create();
        }
        mAlertDialog.show();

    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        EventBus.getDefault().post(new ActivityEvent(EventCode.Main.HIDE_GP));
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        EventBus.getDefault().post(new ActivityEvent(EventCode.Main.SHOW_GP));
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
            mAgentWeb.getWebCreator().get().destroy();
        }
    }

    @Override
    protected boolean enableLazy() {
        return false;
    }

    @Override
    public void onSimpleBackClick() {
        if (!mAgentWeb.getWebCreator().get().canGoBack()) {
            pop();
        } else {
            mAgentWeb.back();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport()) {
            return true;
        } else if (mAgentWeb.getWebCreator().get().canGoBack()) {
            mAgentWeb.back();
            return true;
        }
        return false;
    }

    @Override
    public Integer[] onBindBarRightIcon() {
        return new Integer[]{R.drawable.ic_common_share};
    }

    @Override
    public void onRight1Click(View v) {
        super.onRight1Click(v);
        Bitmap favicon = mAgentWeb.getWebCreator().get().getFavicon();
        String title = mAgentWeb.getWebCreator().get().getTitle();
        UMWeb web = new UMWeb(mPath);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(mActivity, favicon));  //缩略图
        web.setDescription("倾听");//描述
        EventBus.getDefault().post(new ActivityEvent(EventCode.Main.SHARE, new ShareAction(mActivity).withMedia(web)));
    }
}
