package com.bilibili.base;

/**
 * Created by jiayiyang on 17/5/8.
 */

public interface IBaseMvpActivity<T extends AbsBasePresenter> extends IBaseActivity {

    T getPresenter();

}
