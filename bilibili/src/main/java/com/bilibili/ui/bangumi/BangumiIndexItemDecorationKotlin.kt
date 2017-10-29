package com.bilibili.ui.bangumi

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.bilibili.R
import com.bilibili.ui.bangumi.viewbinder.BangumiDividerBinderKotlin

/**
 * Created by miserydx on 17/7/2.
 */

class BangumiIndexItemDecorationKotlin(private val spanSizeLookup: GridLayoutManager.SpanSizeLookup) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildLayoutPosition(view)
        if (spanSizeLookup.getSpanSize(position) == 3) {
            //追翻页列表前两项为静态item
            if (position > 1) {
                if (view.tag !is BangumiDividerBinderKotlin.BangumiDivider) {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
                }
                outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
            }
        } else {
            val spanIndex = spanSizeLookup.getSpanIndex(position, 3)
            when (spanIndex) {
                0 -> {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_min)
                }
                1 -> {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_7)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_7)
                }
                2 -> {
                    outRect.left = view.context.resources.getDimensionPixelSize(R.dimen.margin_min)
                    outRect.right = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
                }
            }
            outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.margin_15)
        }
    }

}
