package com.bilibili.widget.banner;

import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Android_ZzT on 17/6/4.
 */

public abstract class BannerAdapter<T, V extends View> extends PagerAdapter {

    public static int INVALID_ID = 0;

    protected List<T> data;

    public BannerAdapter() {}

    public BannerAdapter(List<T> data, boolean isCirculate) {
        setData(data, isCirculate);
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
        V itemView;
        if (layoutId == INVALID_ID) {
            itemView = getItemView();
        } else {
            itemView = (V) LayoutInflater.from(container.getContext()).inflate(layoutId, null);
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
        container.removeView((V) object);
    }

    public void setData(List<T> data, boolean isCirculate) {
        this.data = data;
        if (isCirculate) {
            T first = data.get(0);
            T last = data.get(data.size() - 1);
            this.data.add(data.size(), first);
            this.data.add(0, last);
        }
        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract V getItemView();

    protected abstract void bindData(V itemView, T item);

}
