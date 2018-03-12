package com.common.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiayiyang on 17/3/25.
 */

public abstract class AbsBasePresenter<T extends BaseView> implements BasePresenter {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        clearRx();
    }

    @Override
    public void loadData() {
        //do nothing
    }

    //RXjava取消注册，以避免内存泄露
    public void clearRx() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void registerRx(Disposable d) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
