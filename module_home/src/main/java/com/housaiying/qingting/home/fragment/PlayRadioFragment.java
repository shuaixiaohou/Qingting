package com.housaiying.qingting.home.fragment;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.ActivityEvent;
import com.housaiying.qingting.common.event.EventCode;
import com.housaiying.qingting.common.mvvm.view.BaseMvvmFragment;
import com.housaiying.qingting.common.util.QingTingUtil;
import com.housaiying.qingting.common.util.RouterUtil;
import com.housaiying.qingting.common.util.ToastUtil;
import com.housaiying.qingting.home.R;
import com.housaiying.qingting.home.adapter.PlayRadioAdapter;
import com.housaiying.qingting.home.databinding.HomeFragmentPlayRadioBinding;
import com.housaiying.qingting.home.dialog.PlayRadioPopup;
import com.housaiying.qingting.home.dialog.PlaySchedulePopup;
import com.housaiying.qingting.home.mvvm.ViewModelFactory;
import com.housaiying.qingting.home.mvvm.viewmodel.PlayRadioViewModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.live.program.Program;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;
import org.greenrobot.eventbus.EventBus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import static com.lxj.xpopup.enums.PopupAnimation.TranslateFromBottom;
/**
 * Author: housaiying
 * <br/>Date: 2020/3/5 11:16
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
@Route(path = Constants.Router.Home.F_PLAY_RADIIO)
public class PlayRadioFragment extends BaseMvvmFragment<HomeFragmentPlayRadioBinding, PlayRadioViewModel> implements
        View.OnClickListener, PlaySchedulePopup.onSelectedListener, IXmPlayerStatusListener,
        IXmAdsStatusListener, OnSeekChangeListener, View.OnTouchListener {
    boolean isTouch;
    private PlaySchedulePopup mSchedulePopup;
    private XmPlayerManager mPlayerManager = XmPlayerManager.getInstance(mActivity);
    private Schedule mSchedule;
    private boolean isPlaying;
    private PlayRadioPopup mPlayRadioPopup;
    private Runnable touchRunable = new Runnable() {
        @Override
        public void run() {
            isTouch = false;
        }
    };
    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_play_radio;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView.setBackgroundColor(Color.WHITE);
    }
    @Override
    protected boolean enableSwipeBack() {
        return false;
    }
    @Override
    protected void loadView() {
        super.loadView();
        clearStatus();
    }
    @Override
    protected void initView() {
        mSimpleTitleBar.getLeftCustomView().findViewById(R.id.iv_left).setRotation(-90);
        mBinding.ivItemPlay.setVisibility(View.GONE);
        mSchedulePopup = new PlaySchedulePopup(mActivity, this);
        mHandler.postDelayed(() -> {
            if (mPlayerManager.isPlaying()) {
                if (mPlayerManager.isAdPlaying()) {
                    bufferingAnim();
                } else {
                    playingAnim();
                }
            }
        }, 100);

        mPlayRadioPopup = new PlayRadioPopup(mActivity);
    }
    @Override
    public void initListener() {
        super.initListener();
        mBinding.ivHistory.setOnClickListener(this);
        mBinding.tvHistory.setOnClickListener(this);
        mBinding.tvPlayList.setOnClickListener(this);
        mBinding.ivPlayList.setOnClickListener(this);
        mBinding.flPlayPause.setOnClickListener(this);
        mBinding.lavNext.setOnClickListener(this);
        mBinding.lavPre.setOnClickListener(this);
        mPlayerManager.addPlayerStatusListener(this);
        mPlayerManager.addAdsStatusListener(this);
        mBinding.isbProgress.setOnSeekChangeListener(this);
        mBinding.isbProgress.setOnTouchListener(this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        mHandler.postDelayed(() -> {
            try {
                mSchedule = (Schedule) mPlayerManager.getCurrSound();
                setTitle(new String[]{mSchedule.getRadioName()});
                mBinding.tvRadioName.setText(mSchedule.getRadioName());
                mBinding.tvPlaycount.setText(QingTingUtil.toWanYi(mSchedule.getRadioPlayCount()) + "人听过");
                mViewModel.getPrograms(String.valueOf(mSchedule.getRadioId()));
                mBinding.tvTime.setText(mSchedule.getStartTime().substring(mSchedule.getStartTime().length() - 5) + "~"
                        + mSchedule.getEndTime().substring(mSchedule.getEndTime().length() - 5));
                mBinding.isbProgress.setUserSeekAble(mPlayerManager.getCurrPlayType() == XmPlayListControl.PLAY_SOURCE_TRACK);
                Program program = mSchedule.getRelatedProgram();
                mBinding.tvProgramName.setText(program.getProgramName());
                Glide.with(this).load(program.getBackPicUrl()).into(mBinding.ivCover);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < program.getAnnouncerList().size(); i++) {
                    sb.append(program.getAnnouncerList().get(i).getNickName());
                    if (i != program.getAnnouncerList().size() - 1) {
                        sb.append(",");
                    }
                }
                mBinding.tvAnnouncerName.setText("主播: "
                        + (TextUtils.isEmpty(sb.toString()) ? mSchedule.getRadioName() : sb.toString()));
                initProgress(mPlayerManager.getPlayCurrPositon(), mPlayerManager.getDuration());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("网络异常");
                pop();
            }
        }, 200);

    }
    private void initProgress(int cur, int dur) {
        if (BaseUtil.isInTime(mSchedule.getStartTime() + "-" + mSchedule.getEndTime()) == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm", Locale.getDefault());
            try {
                long start = sdf.parse(mSchedule.getStartTime()).getTime();
                long end = sdf.parse(mSchedule.getEndTime()).getTime();
                cur = (int) (System.currentTimeMillis() - start);
                dur = (int) (end - start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mBinding.tvCurrent.setText(QingTingUtil.secondToTimeE(cur / 1000));
        mBinding.tvDuration.setText(QingTingUtil.secondToTimeE(dur / 1000));
        if (!isTouch) {
            mBinding.isbProgress.setMax((float) dur / 1000);
            mBinding.isbProgress.setProgress((float) cur / 1000);
        }
    }
    @Override
    public void initViewObservable() {
        mViewModel.getProgramsEvent().observe(this, programList ->
                mBinding.tvDesc.setText(getString(R.string.playing,
                        programList.getmProgramList().get(0).getProgramName())));
        mViewModel.getYestodayEvent().observe(this, schedules ->
                mPlayRadioPopup.getYestodayAdapter().setNewData(schedules));
        mViewModel.getTodayEvent().observe(this, schedules ->
                mPlayRadioPopup.getTodayAdapter().setNewData(schedules));
        mViewModel.getTomorrowEvent().observe(this, schedules ->
                mPlayRadioPopup.getTomorrowAdapter().setNewData(schedules));
        mViewModel.getPauseAnimEvent().observe(this, aVoid -> pauseAnim());
    }
    private void updatePlayStatus() {
        updatePlayStatus(mPlayRadioPopup.getYestodayAdapter());
        updatePlayStatus(mPlayRadioPopup.getTodayAdapter());
    }
    private void updatePlayStatus(PlayRadioAdapter adapter) {
        if (adapter == null) {
            return;
        }
        Schedule schedule = (Schedule) mPlayerManager.getCurrSound();
        if (null == schedule) {
            return;
        }
        List<Schedule> schedules = adapter.getData();
        for (int i = 0; i < schedules.size(); i++) {
            LottieAnimationView lavPlaying = (LottieAnimationView) adapter
                    .getViewByPosition(i, R.id.lav_playing);
            TextView tvTitle = (TextView) adapter
                    .getViewByPosition(i, R.id.tv_title);
            if (null != lavPlaying && tvTitle != null) {
                if (schedules.get(i).getDataId() == schedule.getDataId()) {
                    lavPlaying.setVisibility(View.VISIBLE);
                    tvTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    if (XmPlayerManager.getInstance(mActivity).isPlaying()) {
                        lavPlaying.playAnimation();
                    } else {
                        lavPlaying.pauseAnimation();
                    }
                } else {
                    lavPlaying.cancelAnimation();
                    tvTitle.setTextColor(mActivity.getResources().getColor(R.color.textColorPrimary));
                    lavPlaying.setVisibility(View.GONE);
                }
            } else {
                adapter.notifyItemChanged(i);
            }
        }
    }
    @Override
    public SimpleBarStyle onBindBarLeftStyle() {
        return SimpleBarStyle.LEFT_ICON;
    }
    @Override
    public Integer onBindBarLeftIcon() {
        return R.drawable.ic_common_titlebar_back;
    }
    @Override
    public Integer[] onBindBarRightIcon() {
        return new Integer[]{R.drawable.ic_home_dingshi};
    }
    @Override
    public void onRight1Click(View v) {
        super.onRight1Click(v);
        new XPopup.Builder(getContext()).setPopupCallback(new SimpleCallback() {
            @Override
            public void beforeShow() {
                super.beforeShow();
                mSchedulePopup.getScheduleAdapter().notifyDataSetChanged();
            }
        }).asCustom(mSchedulePopup).show();
    }
    @Override
    public void onLeftIconClick(View v) {
        super.onLeftIconClick(v);
        pop();
    }
    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport()) {
            return true;
        } else if (mSchedulePopup != null && mSchedulePopup.getPickerView() != null && mSchedulePopup.getPickerView().isShowing()) {
            mSchedulePopup.getPickerView().dismiss();
            return true;
        }
        return false;
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
    protected boolean enableLazy() {
        return false;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_history || id == R.id.tv_history) {
            pop();
            RouterUtil.navigateTo(Constants.Router.Listen.F_HISTORY);
        } else if (id == R.id.iv_play_list || id == R.id.tv_play_list) {
            if (mSchedule == null) {
                return;
            }
            new XPopup.Builder(getContext()).popupAnimation(TranslateFromBottom).setPopupCallback(new SimpleCallback() {
                @Override
                public void onCreated() {
                    super.onCreated();
                    mViewModel.getSchedules(String.valueOf(mSchedule.getRadioId()));
                }
            }).enableDrag(false).asCustom(mPlayRadioPopup).show();
        } else if (R.id.fl_play_pause == id) {
            if (mPlayerManager.isPlaying()) {
                mPlayerManager.pause();
            } else {
                mPlayerManager.play();
            }
        } else if (R.id.lav_pre == id) {
            mBinding.lavPre.playAnimation();
            if (mPlayerManager.hasPreSound()) {
                mPlayerManager.playPre();
            } else {
                ToastUtil.showToast("没有更多内容");
            }
        } else if (R.id.lav_next == id) {
            mBinding.lavNext.playAnimation();
            if (mPlayerManager.hasNextSound()) {
                mPlayerManager.playNext();
            } else {
                ToastUtil.showToast("没有更多内容");
            }
        }
    }
    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        mHandler.postDelayed(() -> {
            if (mSchedule == null) {
                return;
            }
            if (mPlayRadioPopup.getYestodayAdapter() != null) {
                mViewModel.getSchedules(mSchedule.getRadioName());
            }
        }, 100);
    }
    @Override
    public void onSelected(int type, long time) {
    }
    @Override
    public void onStartGetAdsInfo() {
    }
    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
    }
    @Override
    public void onAdsStartBuffering() {
        bufferingAnim();
    }
    @Override
    public void onAdsStopBuffering() {
    }
    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        bufferingAnim();
    }
    @Override
    public void onCompletePlayAds() {
    }
    @Override
    public void onError(int i, int i1) {
    }
    @Override
    public void onPlayStart() {
        updatePlayStatus();
        if (!mPlayerManager.isBuffering()) {
            playAnim();
        }
    }
    @Override
    public void onPlayPause() {
        updatePlayStatus();
        pauseAnim();
    }
    @Override
    public void onPlayStop() {
        updatePlayStatus();
        pauseAnim();
    }
    @Override
    public void onSoundPlayComplete() {
        updatePlayStatus();
        mViewModel.onPlayComplete(mPlayerManager);
    }
    @Override
    public void onSoundPrepared() {
        updatePlayStatus();
    }
    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        updatePlayStatus();
        initData();
    }
    @Override
    public void onBufferingStart() {
        if (mPlayerManager.isPlaying()) {
            bufferingAnim();
        }
    }
    @Override
    public void onBufferingStop() {
        if (mPlayerManager.isPlaying()) {
            playAnim();
        } else {
            pauseAnim();
        }
    }
    @Override
    public void onBufferProgress(int i) {
    }
    @Override
    public void onPlayProgress(int i, int i1) {
        if (mSchedule == null) {
            return;
        }
        initProgress(i, i1);
    }
    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }
    private void playAnim() {
        if (!isPlaying) {
            mBinding.lavPlayPause.setMinAndMaxFrame(55, 90);
            mBinding.lavPlayPause.loop(false);
            mBinding.lavPlayPause.playAnimation();
            mBinding.lavBuffering.cancelAnimation();
            mBinding.lavBuffering.setVisibility(View.GONE);
            mBinding.lavPlayPause.setVisibility(View.VISIBLE);
            mBinding.lavPlayPause.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    playingAnim();
                    mBinding.lavPlayPause.removeAnimatorListener(this);
                }
            });
        }
    }
    private void playingAnim() {
        mBinding.lavPlayPause.removeAllAnimatorListeners();
        isPlaying = true;
        mBinding.lavPlayPause.setMinAndMaxFrame(90, 170);
        mBinding.lavPlayPause.loop(true);
        mBinding.lavPlayPause.playAnimation();
        mBinding.lavBuffering.cancelAnimation();
        mBinding.lavBuffering.setVisibility(View.GONE);
        mBinding.lavPlayPause.setVisibility(View.VISIBLE);
    }
    private void bufferingAnim() {
        mBinding.lavPlayPause.cancelAnimation();
        mBinding.lavBuffering.playAnimation();
        isPlaying = false;
        mBinding.lavPlayPause.setVisibility(View.GONE);
        mBinding.lavBuffering.setVisibility(View.VISIBLE);
    }
    private void pauseAnim() {
        mBinding.lavBuffering.cancelAnimation();
        mBinding.lavPlayPause.removeAllAnimatorListeners();
        isPlaying = false;
        mBinding.lavPlayPause.setMinAndMaxFrame(180, 210);
        mBinding.lavPlayPause.loop(false);
        mBinding.lavPlayPause.playAnimation();
        mBinding.lavBuffering.setVisibility(View.GONE);
        mBinding.lavPlayPause.setVisibility(View.VISIBLE);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSeeking(SeekParams seekParams) {
        TextView indicator = mBinding.isbProgress.getIndicator().getTopContentView().findViewById(R.id.tv_indicator);
        indicator.setText(QingTingUtil.secondToTimeE(seekParams.progress)
                + "/" + QingTingUtil.secondToTimeE((long) seekParams.seekBar.getMax()));
    }
    @Override
    public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
        isTouch = true;
    }
    @Override
    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
        mPlayerManager.seekTo(seekBar.getProgress() * 1000);
        mHandler.postDelayed(touchRunable, 200);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerManager.removePlayerStatusListener(this);
        mPlayerManager.removeAdsStatusListener(this);
    }
    @Override
    public Class<PlayRadioViewModel> onBindViewModel() {
        return PlayRadioViewModel.class;
    }
    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHandler.removeCallbacks(touchRunable);
            isTouch = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
        }
        return false;
    }
}