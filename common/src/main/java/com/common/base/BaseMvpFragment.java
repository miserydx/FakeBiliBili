package com.common.base;

import javax.inject.Inject;

/**
 * Created by jiayiyang on 17/4/14.
 */

public abstract class BaseMvpFragment<T extends AbsBasePresenter> extends BaseFragment implements BaseView{

    @Inject
    protected T mPresenter;

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.attachView(this);
        mPresenter.loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.releaseData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }

}
