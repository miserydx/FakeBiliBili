package com.bilibili.ui.live.liveplay

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.WindowManager
import butterknife.BindView
import com.bilibili.App
import com.bilibili.R
import com.bilibili.widget.video.BiliLiveVideoView
import com.common.base.IBaseActivity
import com.common.util.StatusBarUtil
import me.yokeyword.fragmentation.SupportActivity

class LivePlayActivity : SupportActivity(), IBaseActivity {

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

    @BindView(R.id.video_layout)
    lateinit var videoView: BiliLiveVideoView

    private val URL = "http://live-play.acgvideo.com/live/962/live_1606803_1664710.flv?wsSecret=fc8b5a59f9e5db3e87137a5edebe0f1b&wsTime=598de0ed"


    override fun getLayoutId(): Int = R.layout.activity_live_play

    override fun initInject() {
        App.getInstance().activityComponent.inject(this)
    }

    override fun initViewAndEvent() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val dynamicUrl = intent.getStringExtra(TYPE_LIVE_URL)
        val roomId = intent.getStringExtra(TYPE_LIVE_ROOM_ID)
        videoView.init(null, null, dynamicUrl, roomId)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        videoView.onConfigurationChanged(newConfig)
    }
}
