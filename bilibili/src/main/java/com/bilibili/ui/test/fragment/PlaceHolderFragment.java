package com.bilibili.ui.test.fragment;

import android.widget.TextView;

import com.bilibili.R;
import com.common.base.BaseFragment;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by miserydx on 17/12/19.
 */

public class PlaceHolderFragment extends BaseFragment {

    @BindView(R.id.tv_place_holder)
    TextView tvPlaceHolder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_placeholder;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {
        tvPlaceHolder.setText("敬请期待");
    }
}
