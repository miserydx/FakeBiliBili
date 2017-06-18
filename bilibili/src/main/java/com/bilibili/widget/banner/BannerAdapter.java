package com.bilibili.widget.banner;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Android_ZzT on 17/6/4.
 */

public abstract class BannerAdapter<T> extends PagerAdapter {

    public static int INVALID_ID = 0;

    protected Context context;

    protected List<T> data;

    protected boolean isCirculate;

    public BannerAdapter(Context context, List<T> data, boolean isCirculate) {
        this.context = context;
        this.isCirculate = isCirculate;
        this.data = data;
        if (isCirculate) {
            T first = data.get(0);
            T last = data.get(data.size() - 1);
            this.data.add(data.size(), first);
            this.data.add(0, last);
        }
    }

    @Override
    public int getCount() {
        return data == null ? 0: data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int layoutId = getLayoutId();
        View itemView;
        if (layoutId == INVALID_ID) {
            itemView = getItemView();
        } else {
            itemView = LayoutInflater.from(context).inflate(layoutId, null);
        }
        if (itemView == null) {
            throw new RuntimeException("itemView can not be null,check getLayoutId or getItemView");
        }
        bindData(itemView, data.get(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract View getItemView();

    protected abstract void bindData(View itemView, T item);

}
