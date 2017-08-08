package com.bilibili.ui.test.activity

import android.content.Intent
import android.view.View
import android.widget.Button

import com.bilibili.R
import com.bilibili.ui.main.MainActivity
import com.common.base.IBaseActivity
import com.common.util.StatusBarUtil

import butterknife.BindView
import butterknife.OnClick
import me.yokeyword.fragmentation.SupportActivity

class NavigationTestActivity : SupportActivity(), IBaseActivity {

    override fun getLayoutId(): Int {
        return R.layout.activity_test_navigation
    }

    override fun initInject() {}

    override fun initViewAndEvent() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.theme_color_primary))
    }

    @OnClick(R.id.toolbar_behavior_mvp_btn, R.id.news_btn, R.id.status_picture_mvp_btn, R.id.scroll_gradient_mvp_btn, R.id.test_api_btn, R.id.test_no_base_btn, R.id.test_no_base_mvp_btn, R.id.main_btn, R.id.test_player_btn)
    fun jumpToPage(view: View) {
        when (view.id) {
            R.id.toolbar_behavior_mvp_btn -> startActivity(Intent(this, ToolbarBehaviorActivity::class.java))
            R.id.news_btn -> startActivity(Intent(this, NewsActivity::class.java))
            R.id.status_picture_mvp_btn -> startActivity(Intent(this, StatusWithPictureActivity::class.java))
            R.id.scroll_gradient_mvp_btn -> startActivity(Intent(this, ScrollGradientActivity::class.java))
            R.id.test_api_btn -> startActivity(Intent(this, TestApiActivity::class.java))
            R.id.test_no_base_btn -> startActivity(Intent(this, TestNoBaseActivity::class.java))
            R.id.test_no_base_mvp_btn -> startActivity(Intent(this, TestNoBaseMvpActivity::class.java))
            R.id.main_btn -> startActivity(Intent(this, MainActivity::class.java))
            R.id.test_player_btn -> startActivity(Intent(this, TestPlayerActivity::class.java))
        }
    }

}
