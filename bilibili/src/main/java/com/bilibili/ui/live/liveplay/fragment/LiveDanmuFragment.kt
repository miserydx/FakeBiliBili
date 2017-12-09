package com.bilibili.ui.live.liveplay.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import butterknife.BindView
import com.bilibili.R
import com.bilibili.ui.live.liveplay.LiveDanMuCallback
import com.bilibili.ui.live.liveplay.LiveDanMuMsgCallback
import com.bilibili.ui.live.liveplay.fragment.binder.LiveDanmuItemBinder
import com.bilibili.util.CommonConsumer
import com.bilibili.util.RxJavaUtil
import com.bilibili.widget.danmu.live.LiveDanMuReceiver
import com.bilibili.widget.danmu.live.entity.DanMuMSGEntity
import com.bilibili.widget.recyclerview.BiliMultiTypeAdapter
import com.common.base.BaseFragment
import me.drakeet.multitype.Items
import java.io.IOException
import java.util.*

/**
 * Created by miserydx on 17/10/26.
 */
class LiveDanmuFragment : BaseFragment() {

    companion object {

        fun newInstance(): LiveDanmuFragment {
            val fragment = LiveDanmuFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    @BindView(R.id.rv)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: BiliMultiTypeAdapter
    private var items = Items()
    private lateinit var LiveDanmuFragmentCallback: LiveDanMuCallback

    override fun getLayoutId(): Int = R.layout.fragment_live_danmu

    override fun initInject() {

    }

    override fun initViewAndEvent() {
        mAdapter = BiliMultiTypeAdapter()
        //register item
        mAdapter.register(String::class.java, LiveDanmuItemBinder())
        mAdapter.items = items
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
        connectLiveDanmu()
    }

    /**
     * 连接弹幕
     */
    private fun connectLiveDanmu() {
        LiveDanmuFragmentCallback = object : LiveDanMuCallback() {
            override fun onDanMuMSGPackage(danMuMSGEntity: DanMuMSGEntity) {
                super.onDanMuMSGPackage(danMuMSGEntity)
                RxJavaUtil.runOnUiThread {
                    val string: String = danMuMSGEntity.senderNick + "：" + danMuMSGEntity.danMuContent
                    items.add(string)
                    mAdapter.notifyDataSetChanged()
                    if (mRecyclerView != null) {
                        mRecyclerView.scrollToPosition(items.size - 1)
                    }
                }
            }
        }
        LiveDanMuReceiver.getInstance()
                .addCallback(LiveDanmuFragmentCallback)
    }

    override fun onDestroyView() {
        LiveDanMuReceiver.getInstance().removeCallback(LiveDanmuFragmentCallback)
        super.onDestroyView()
    }

}