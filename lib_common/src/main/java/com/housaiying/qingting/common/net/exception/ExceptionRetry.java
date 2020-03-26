package com.housaiying.qingting.common.net.exception;


import android.util.Log;

import com.google.gson.Gson;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.bean.UserBean;
import com.housaiying.qingting.common.db.DBManager;
import com.housaiying.qingting.common.net.NetManager;
import com.housaiying.qingting.common.net.RxAdapter;
import com.housaiying.qingting.common.net.dto.LoginDTO;
import com.housaiying.qingting.common.net.dto.ResponseDTO;
import com.housaiying.qingting.common.util.log.TLog;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/1 8:01
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:所有异常都会经过此处,可拦截需要重试的内部异常,如Token超时等
 */
public class ExceptionRetry implements Function<Observable<Throwable>, Observable<?>> {
    private DBManager mDBManager = DBManager.getInstance();

    @Override
    public Observable<?> apply(Observable<Throwable> observable) throws Exception {

        return observable.compose(upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()))
                .flatMap((Function<Throwable, Observable<?>>) throwable -> {
                    TLog.d(Log.getStackTraceString(throwable));
                    return Observable.error(throwable);
                });
    }

    /**
     * 重新登陆
     *
     * @return
     */
    private Observable reLogin() {
        return mDBManager.getSPString(Constants.SP.USER)
                .map(s -> {
                    final UserBean userBean = new Gson().fromJson(s, UserBean.class);
                    LoginDTO loginDTO = new LoginDTO();
                    loginDTO.setCode(userBean.getCode());
                    loginDTO.setDescer_name(userBean.getDescer_name());
                    loginDTO.setDescer_phone(userBean.getDescer_phone());
                    loginDTO.setGraer_name(userBean.getGraer_name());
                    loginDTO.setGraer_phone(userBean.getGraer_phone());
                    return loginDTO;
                }).flatMap((Function<LoginDTO, ObservableSource<ResponseDTO<UserBean>>>) loginDTO ->
                        NetManager.getInstance().getUserService().login(loginDTO))
                .flatMap((Function<ResponseDTO<UserBean>, Observable<?>>) responseDTO -> {
                    if (!responseDTO.code.equals(ExceptionConverter.APP_ERROR.SUCCESS)) {
                        return Observable.error(new CustException(responseDTO.code,
                                responseDTO.msg));
                    } else {
                        return mDBManager.putSP(Constants.SP.TOKEN, responseDTO.result.getToken())
                                .flatMap((Function<String, ObservableSource<String>>) aBoolean ->
                                        mDBManager.putSP(Constants.SP.USER, new Gson().toJson(responseDTO.result)));
                    }
                })
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
