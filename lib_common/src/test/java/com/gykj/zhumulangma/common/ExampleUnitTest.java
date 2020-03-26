package com.gykj.zhumulangma.common;

import com.housaiying.qingting.common.net.exception.ExceptionRetry;

import org.junit.Test;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static String mString;
    String s;

    private static Observable t() {

        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                mString = "aa";
                mString = "11";

            }
        }).doOnDispose(new Action() {

            @Override
            public void run() throws Exception {
                System.out.println(mString);
            }
        });
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {

                    emitter.onNext(System.currentTimeMillis() + "");
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String aVoid) throws Exception {
                        System.out.println(aVoid);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(11);
                    }
                }).retryWhen(new ExceptionRetry());

        stringObservable.subscribe();
    }

    @Test
    public void test2() {


        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(7));


        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(calendar1.get(7));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(calendar2.get(7));
    }

}