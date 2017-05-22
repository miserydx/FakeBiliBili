package com.bilibili.ui.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bilibili.R;
import com.bilibili.base.IBaseActivity;
import com.bilibili.di.component.ActivityComponent;

public class TestNoBaseActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_no_base;
    }

    @Override
    public View getPaddingNeedView() {
        return null;
    }

    @Override
    public boolean setCustomStatusBar() {
        return false;
    }

    @Override
    public void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void initViewAndEvent() {

    }
}
