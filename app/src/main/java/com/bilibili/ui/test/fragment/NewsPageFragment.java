package com.bilibili.ui.test.fragment;

import com.bilibili.R;
import com.bilibili.base.BaseFragment;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsPageFragment extends BaseFragment {


    @Override
    protected int setContentView() {
        return R.layout.fragment_page_news;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {

    }

}
