package com.bilibili.util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miserydx on 17/10/24.
 */

public class RxJavaUtil {

    @SuppressWarnings("unchecked")
    public static void runOnWorkThread(Consumer consumer) {
        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .subscribe(consumer);
    }

    @SuppressWarnings("unchecked")
    public static void runOnUiThread(Consumer consumer) {
        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

}
