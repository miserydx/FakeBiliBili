package com.common.base.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Android_ZzT on 17/5/31.
 */

public abstract class OnItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mDetector;

    public OnItemClickListener(@NonNull final RecyclerView recyclerView) {
        mDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                onItemClick(itemView, recyclerView.getChildLayoutPosition(itemView));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                onItemLongClick(itemView, recyclerView.getChildLayoutPosition(itemView));
            }
        });
    }

    public abstract void onItemClick(View itemView, int position);

    public abstract void onItemLongClick(View itemView, int position);

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
