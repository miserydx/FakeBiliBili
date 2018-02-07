package com.common.widget.adapter;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Android_ZzT on 17/7/23.
 */

public abstract class BaseLoadMoreBinder<VH extends BaseViewHolder> extends LoadViewBinder<VH> {

    private DefaultAdapterWrapper.OnClickRetryListener onClickRetryListener;

    private boolean isLoading = false;

    public final void loadMoreComplete() {
        isLoading = false;
    }

    public final void setOnClickRetryListener(DefaultAdapterWrapper.OnClickRetryListener onClickRetryListener) {
        this.onClickRetryListener = onClickRetryListener;
    }

    static final int STATE_LOAD_MORE = 1;
    static final int STATE_NO_MORE = 2;
    static final int STATE_LOAD_FAIL = 3;

    private int state = STATE_LOAD_MORE;

    protected int getState() {
        return state;
    }

    protected void setState(int state) {
        this.state = state;
    }

    @Override
    protected final void onBindViewHolder(@NonNull final VH holder) {
        switch (getState()) {
            case STATE_LOAD_MORE:
                onLoadMore(holder);
                break;
            case STATE_NO_MORE:
                onNoMore(holder);
                break;
            case STATE_LOAD_FAIL:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickRetryListener != null && state == STATE_LOAD_FAIL) {
                            setState(STATE_LOAD_MORE);
                            onBindViewHolder(holder);
                            isLoading = true;
                            onClickRetryListener.onClickRetry();
                        }
                    }
                });
                onLoadFailed(holder);
                break;
        }
    }

    protected abstract void onLoadMore(VH holder);

    protected abstract void onNoMore(VH holder);

    protected abstract void onLoadFailed(VH holder);
}
