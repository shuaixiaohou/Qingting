package com.housaiying.qingting.common.net.exception;


import android.util.Log;

import com.housaiying.qingting.common.db.DBManager;
import com.housaiying.qingting.common.util.log.TLog;

import io.reactivex.Observable;
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
}
