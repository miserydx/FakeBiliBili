package com.bilibili.ui.live.liveplay

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.*
import butterknife.BindView
import com.bilibili.App
import com.bilibili.R
import com.bilibili.ui.live.liveplay.fragment.LiveDanmuFragment
import com.bilibili.ui.test.fragment.PlaceHolderFragment
import com.bilibili.util.InflateUtil
import com.bilibili.widget.video.LiveVideoPlayer
import com.common.base.IBaseMvpActivity
import com.flyco.tablayout.SlidingTabLayout
import com.team.ijkplayer.player.DXVideoView
import me.yokeyword.fragmentation.SupportActivity
import java.util.ArrayList
import javax.inject.Inject

class LivePlayActivity : SupportActivity(), IBaseMvpActivity<LivePlayPresenter>, LivePlayContract.View,
        DXVideoView.OnPreparedListener, DXVideoView.OnStartListener, DXVideoView.OnPauseListener {

    companion object {

        private val TYPE_LIVE_URL = "type_live_url"
        private val TYPE_LIVE_ROOM_ID = "type_live_room_id"

        fun startActivity(context: Context, url: String, roomId: String) {
            val intent = Intent(context, LivePlayActivity::class.java)
            intent.putExtra(TYPE_LIVE_URL, url)
            intent.putExtra(TYPE_LIVE_ROOM_ID, roomId)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var mPresenter: LivePlayPresenter
    @BindView(R.id.live_video_player)
    lateinit var videoPlayer: LiveVideoPlayer
    @BindView(R.id.tab_layout)
    lateinit var tabLayout: SlidingTabLayout
    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    private lateinit var adapter: LivePlayPagerAdapter
    private val mFragments = ArrayList<Fragment>()
    private lateinit var mTitles: Array<String>

    override fun getLayoutId(): Int = R.layout.activity_live_play

    override fun initInject() {
        App.getInstance().activityComponent.inject(this)
    }

    override fun getPresenter(): LivePlayPresenter = mPresenter

    override fun initViewAndEvent() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val dynamicUrl = intent.getStringExtra(TYPE_LIVE_URL)
        val roomId = intent.getStringExtra(TYPE_LIVE_ROOM_ID)
        initToolbar()
        videoPlayer.setOnPreparedListener(this)
        videoPlayer.setOnStartListener(this)
        videoPlayer.setOnPauseListener(this)
        videoPlayer.setUp(dynamicUrl)
        videoPlayer.initDanmakuView(roomId)
        mTitles = resources.getStringArray(R.array.live_play)
        initChildFragment()
        adapter = LivePlayPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setViewPager(viewPager, mTitles)
    }

    private fun initToolbar() {
        val mToolbar = InflateUtil.inflate(layoutInflater, R.layout.layout_live_toolbar) as Toolbar
        mToolbar.title = ""
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        mToolbar.setNavigationOnClickListener { finish() }
        mToolbar.setPadding(0, com.common.util.StatusBarUtil.getStatusBarHeight(this), 0, 0)
        videoPlayer.setupToolbar(mToolbar)
    }

    private fun initChildFragment() {
        mFragments.add(LiveDanmuFragment())
        mFragments.add(PlaceHolderFragment())
        mFragments.add(PlaceHolderFragment())
    }

    override fun onPause() {
        videoPlayer.playOrPause()
        super.onPause()
    }

    override fun onResume() {
        videoPlayer.playOrPause()
        super.onResume()
    }

    override fun onDestroy() {
        videoPlayer.release()
        super.onDestroy()
    }

    override fun onBackPressedSupport() {
        if (videoPlayer.onBackPressed()) {
            return
        }
        super.onBackPressedSupport()
    }

    override fun onPrepared() {

    }

    override fun onVideoStart() {

    }

    override fun onVideoPause() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.live_menu, menu)
        return true
    }

    private inner class LivePlayPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence = mTitles[position]

        override fun getItem(position: Int): Fragment = mFragments[position]
    }

}