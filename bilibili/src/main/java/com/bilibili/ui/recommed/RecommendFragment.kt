package com.bilibili.ui.recommed

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.bilibili.App
import com.bilibili.R
import com.bilibili.model.bean.AppIndex
import com.bilibili.ui.recommed.viewbinder.RecommendBannerItemViewBinder
import com.bilibili.ui.recommed.viewbinder.RecommendIndexItemBinder
import com.bilibili.widget.recyclerview.BiliMultiTypeAdapter
import com.common.base.BaseMvpFragment

import butterknife.BindView
import butterknife.OnClick
import me.drakeet.multitype.Items

/**
 * Created by miserydx on 17/7/6.
 */

class RecommendFragment : BaseMvpFragment<RecommendPresenter>(), RecommendContract.View {

    @BindView(R.id.rv)
    lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.layout_refresh)
    lateinit var mRefreshLayout: SwipeRefreshLayout

    private lateinit var mAdapter: BiliMultiTypeAdapter
    private var items = Items()

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }

    override fun initInject() {
        App.getInstance().fragmentComponent.inject(this)
    }

    override fun initViewAndEvent() {
        mRefreshLayout.setColorSchemeResources(R.color.theme_color_primary)
        mRefreshLayout.setOnRefreshListener { pullToRefresh() }
        val layoutManager = GridLayoutManager(context, SPAN_COUNT)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val o = items[position]
                return if (o is AppIndex) 1 else SPAN_COUNT
            }
        }
        layoutManager.spanSizeLookup = spanSizeLookup
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_main))
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(RecommendIndexItemDecoration(spanSizeLookup))
        mAdapter = BiliMultiTypeAdapter()
        //register item
        mAdapter.register(AppIndex::class.java, RecommendIndexItemBinder())
        mAdapter.register(RecommendBannerItemViewBinder.Banner::class.java, RecommendBannerItemViewBinder())
        mAdapter.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as GridLayoutManager
                //列表中LastVisibleItem为倒数第二行时，加载更多
                if (manager.findLastVisibleItemPosition() + SPAN_COUNT >= manager.itemCount) {
                    mPresenter.loadMore((items[items.size - 1] as AppIndex).idx - 1)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        mRecyclerView.adapter = mAdapter
    }

    override fun onDataUpdated(items: Items, state: Int) {
        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }
        when (state) {
            RecommendPresenter.STATE_INITIAL -> {
                this.items = items
                mAdapter.items = this.items
                mAdapter.notifyDataSetChanged()
            }
            RecommendPresenter.STATE_REFRESHING -> {
                val temp = Items()
                temp.addAll(this.items)
                this.items = items
                this.items.addAll(temp)
                mAdapter.items = this.items
                mAdapter.notifyDataSetChanged()
            }
            RecommendPresenter.STATE_LOAD_MORE -> {
                val position = this.items.size
                this.items.addAll(items)
                mAdapter.items = this.items
                mPresenter.loadingMoreFinished()
                mAdapter.notifyItemInserted(position)
            }
        }
    }

    private fun pullToRefresh() {
        var idx = 0
        if (items[0] is RecommendBannerItemViewBinder.Banner) {
            idx = (items[0] as RecommendBannerItemViewBinder.Banner).idx + 1
        } else if (items[0] is AppIndex) {
            idx = (items[0] as AppIndex).idx + 1
        }
        mPresenter.pullToRefresh(idx)
    }

    override fun onRefreshingStateChanged(isRefresh: Boolean) {
        mRefreshLayout.isRefreshing = isRefresh
    }

    @OnClick(R.id.load_more_tv)
    fun onClick(v: View) {
        when (v.id) {
            R.id.load_more_tv -> {
                mRefreshLayout.isRefreshing = true
                pullToRefresh()
            }
        }
    }

    companion object {

        val TAG = RecommendFragment::class.java.simpleName

        private val SPAN_COUNT = 2
    }

}