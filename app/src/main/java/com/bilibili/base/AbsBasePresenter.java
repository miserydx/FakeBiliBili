package com.bilibili.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiayiyang on 17/3/25.
 */

public abstract class AbsBasePresenter<T extends BaseView> implements BasePresenter {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        unSubscribeRx();
    }

    //RXjava取消注册，以避免内存泄露
    public void unSubscribeRx() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void subscribeRx(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
