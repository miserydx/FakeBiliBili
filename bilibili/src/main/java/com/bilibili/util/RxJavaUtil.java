package com.bilibili.util;

import java.lang.*;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miserydx on 17/10/24.
 */

public class RxJavaUtil {

    @SuppressWarnings("unchecked")
    public static void runOnUiThread(ITask task) {
        Observable.just(task)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ITask>() {
                    @Override
                    public void accept(ITask task) throws Exception {
                        task.execute();
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static void runOnWorkThread(ITask task) {
        Observable.just(task)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<ITask>() {
                    @Override
                    public void accept(ITask task) throws Exception {
                        task.execute();
                    }
                });
    }

}
