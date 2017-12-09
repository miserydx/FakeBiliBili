package com.bilibili.ui.bangumi

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

import com.bilibili.App
import com.bilibili.R
import com.bilibili.widget.recyclerview.BiliMultiTypeAdapter
import com.common.base.BaseMvpFragment

import butterknife.BindView
import com.bilibili.model.bean.BangumiIndexFall
import com.bilibili.model.bean.Foot
import com.bilibili.model.bean.Recommend
import com.bilibili.ui.bangumi.viewbinder.*
import me.drakeet.multitype.Items

/**
 * Created by miserydx on 17/6/29.
 */

class BangumiFragment : BaseMvpFragment<BangumiPresenter>(), BangumiContract.View {

    companion object {
        val TAG = BangumiFragment::class.java.simpleName

        private val SPAN_COUNT = 3
    }

    @BindView(R.id.rv)
    lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.layout_refresh)
    lateinit var mRefreshLayout: SwipeRefreshLayout

    private lateinit var mAdapter: BiliMultiTypeAdapter
    private var items = Items()

    override fun getLayoutId(): Int = R.layout.fragment_bangumi

    override fun initInject() {
        App.getInstance().fragmentComponent.inject(this)
    }

    override fun initViewAndEvent() {
        mRefreshLayout.setColorSchemeResources(R.color.theme_color_primary)
        mRefreshLayout.setOnRefreshListener { mPresenter.pullToRefresh() }
        val layoutManager = GridLayoutManager(context, SPAN_COUNT)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val o = items[position]
                return if (o is Recommend) 1 else SPAN_COUNT
            }
        }
        layoutManager.spanSizeLookup = spanSizeLookup
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(BangumiIndexItemDecoration(spanSizeLookup))
        mAdapter = BiliMultiTypeAdapter()
        //register item
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow::class.java, BangumiIndexFollowBinder())
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow::class.java, BangumiIndexFollowBinder())
        mAdapter.register(BangumiHomeBinder.BangumiHome::class.java, BangumiHomeBinder())
        mAdapter.register(BangumiIndexRecommendBinder.BangumiIndexRecommend::class.java, BangumiIndexRecommendBinder())
        mAdapter.register(Recommend::class.java, BangumiRecommendDetailBinder())
        mAdapter.register(Foot::class.java, BangumiIndexPageFootBinder())
        mAdapter.register(BangumiIndexFall::class.java, BangumiIndexFallBinder())
        mAdapter.register(BangumiDividerBinder.BangumiDivider::class.java, BangumiDividerBinder())
        mAdapter.setFooterItemEnabled(true)
        mAdapter.setOnLoadMoreListener({ mPresenter.loadMore() }, BiliMultiTypeAdapter.LOAD_MORE_MODE_BOTTOM)
        mAdapter.setScrollSaveStrategyEnabled(true)
        mRecyclerView.adapter = mAdapter
    }

    @Synchronized override fun onDataUpdated(items: Items?, state: Int) {
        when (state) {
            BangumiPresenter.STATE_INITIAL -> {
                this.items = items!!
                mAdapter.items = this.items
                mAdapter.notifyDataSetChanged()
            }
            BangumiPresenter.STATE_REFRESHING -> {
                val temp = Items()
                temp.addAll(this.items.subList(items!!.size, this.items.size - 1))
                this.items = items
                this.items.addAll(temp)
                mAdapter.items = this.items
                mAdapter.notifyDataSetChanged()
            }
            BangumiPresenter.STATE_LOAD_MORE -> {
                if (items!!.size == 0) {
                    mAdapter.showNoMore()
                }
                val position = this.items.size
                this.items.addAll(items)
                mAdapter.items = this.items
                mAdapter.setLoadMoreFinished()
                mAdapter.notifyItemInserted(position)
            }
            BangumiPresenter.STATE_LOAD_ERROR -> mAdapter.showFailToLoad()
        }

    }

    override fun onRefreshingStateChanged(isRefresh: Boolean) {
        mRefreshLayout.isRefreshing = isRefresh
    }
}
