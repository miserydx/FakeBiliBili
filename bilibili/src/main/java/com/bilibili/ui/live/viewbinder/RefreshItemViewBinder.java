package com.bilibili.ui.live.viewbinder;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bilibili.R;
import com.bilibili.model.event.LivePartialRefreshEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

import static android.animation.ValueAnimator.INFINITE;

/**
 * Created by Android_ZzT on 17/6/27.
 */

public class RefreshItemViewBinder extends ItemViewBinder<RefreshItemViewBinder.RefreshItem, RefreshItemViewBinder.MoreViewHolder> {

    @NonNull
    @Override
    protected MoreViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_refresh, parent, false);
        return new MoreViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull MoreViewHolder holder, @NonNull RefreshItem item) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(holder.tvRefresh, "rotation", 0f, -360f);
        animator.setDuration(600);
        animator.setRepeatCount(INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animator.isRunning()) {
                    animator.end();
                } else {
                    animator.start();
                    LivePartialRefreshEvent event = new LivePartialRefreshEvent();
                    EventBus.getDefault().post(event);
                }


            }
        });
    }

    static class MoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_container)
        RelativeLayout rlContainer;
        @BindView(R.id.iv_refresh)
        ImageView tvRefresh;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class RefreshItem {

    }
}
