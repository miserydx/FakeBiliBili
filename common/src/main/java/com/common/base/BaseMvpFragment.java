package com.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Created by jiayiyang on 17/4/14.
 */

public abstract class BaseMvpFragment<T extends AbsBasePresenter> extends BaseFragment implements BaseView{

    @Inject
    protected T mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
        mPresenter.loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.releaseData();
        if (mPresenter != null)
            mPresenter.detachView();
    }

}
