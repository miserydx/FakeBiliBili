package com.bilibili.ui.recommed

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.bilibili.R

/**
 * Created by miserydx on 17/7/6.
 */

class RecommendIndexItemDecoration(private val spanSizeLookup: GridLayoutManager.SpanSizeLookup) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildLayoutPosition(view)
        if (spanSizeLookup.getSpanSize(position) == 3) {
            outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_small)
            outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_small)
        } else {
            val spanIndex = spanSizeLookup.getSpanIndex(position, 2)
            when (spanIndex) {
                0 -> {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_small)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_tiny)
                }
                1 -> {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_tiny)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_small)
                }
            }
        }
        outRect.top = view.context.resources.getDimensionPixelSize(R.dimen.margin_10)
        if (position == state!!.itemCount - 1 || position == state.itemCount - 2) {
            outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.margin_tiny)
        }
    }
}
