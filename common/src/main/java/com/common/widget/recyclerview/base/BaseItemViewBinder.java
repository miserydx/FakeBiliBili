package com.common.widget.recyclerview.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 用于BaseAdapter
 * Created by miserydx on 18/1/22.
 */

public abstract class BaseItemViewBinder<T, VH extends BaseViewHolder> extends ItemViewBinder<T, VH> {

    public final VH onCreateVH(@NonNull ViewGroup parent){
        return onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    public final void onBindVH(@NonNull final VH holder, @NonNull final T item) {
        onBindViewHolder(holder, item);
    }

}
