package com.bilibili.ui.main;

import com.common.base.AbsBasePresenter;

import javax.inject.Inject;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class MainPresenter extends AbsBasePresenter<MainContract.View> implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter() {

    }


    @Override
    public void loadData() {

    }

    @Override
    public void releaseData() {

    }
}
