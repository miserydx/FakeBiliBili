package com.bilibili.ui.test.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bilibili.R;
import com.bilibili.ui.main.MainActivity;
import com.common.base.IBaseActivity;
import com.common.base.BaseActivity;
import com.common.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class NavigationTestActivity extends BaseActivity implements IBaseActivity {

    @BindView(R.id.news_btn)
    Button btnNews;
    @BindView(R.id.toolbar_behavior_mvp_btn)
    Button btnToolbarBehavior;
    @BindView(R.id.status_picture_mvp_btn)
    Button btnStatusWithPicture;
    @BindView(R.id.scroll_gradient_mvp_btn)
    Button btnScrollGradient;
    @BindView(R.id.test_api_btn)
    Button btnTestApi;
    @BindView(R.id.test_no_base_btn)
    Button btnTestNoBase;
    @BindView(R.id.test_no_base_mvp_btn)
    Button btnTestNoBaseMvp;
    @BindView(R.id.main_btn)
    Button btnMain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_navigation;
    }

    @Override
    public void initInject() {
    }

    @Override
    public void initViewAndEvent() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color_primary));
    }

    @OnClick({R.id.toolbar_behavior_mvp_btn, R.id.news_btn, R.id.status_picture_mvp_btn, R.id.scroll_gradient_mvp_btn, R.id.test_api_btn,
            R.id.test_no_base_btn, R.id.test_no_base_mvp_btn, R.id.main_btn})
    public void jumpToPage(View view){
        switch (view.getId()){
            case R.id.toolbar_behavior_mvp_btn:
                startActivity(new Intent(this, ToolbarBehaviorActivity.class));
                break;
            case R.id.news_btn:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case R.id.status_picture_mvp_btn:
                startActivity(new Intent(this, StatusWithPictureActivity.class));
                break;
            case R.id.scroll_gradient_mvp_btn:
                startActivity(new Intent(this, ScrollGradientActivity.class));
                break;
            case R.id.test_api_btn:
                startActivity(new Intent(this, TestApiActivity.class));
                break;
            case R.id.test_no_base_btn:
                startActivity(new Intent(this, TestNoBaseActivity.class));
                break;
            case R.id.test_no_base_mvp_btn:
                startActivity(new Intent(this, TestNoBaseMvpActivity.class));
                break;
            case R.id.main_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}