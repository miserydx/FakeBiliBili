package com.bilibili.ui.main.mvp.presenter;

import com.bilibili.base.AbsBasePresenter;
import com.bilibili.ui.main.mvp.contract.MainContract;

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
