package com.bilibili.ui.test.activity;

import android.support.v7.app.AppCompatActivity;

import com.bilibili.R;
import com.bilibili.base.IBaseActivity;
import com.bilibili.di.component.ActivityComponent;
import com.bilibili.util.StatusBarUtil;

public class TestNoBaseActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_no_base;
    }

    @Override
    public void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void initViewAndEvent() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color_primary));
    }
}
