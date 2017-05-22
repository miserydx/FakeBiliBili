package com.bilibili.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.di.component.DaggerFragmentComponent;
import com.bilibili.di.component.FragmentComponent;
import com.bilibili.app.App;
import com.bilibili.di.component.AppComponent;
import com.bilibili.di.module.FragmentModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by jiayiyang on 17/4/14.
 */

abstract public class BaseFragment extends SupportFragment {

    private Context mContext;
    private Unbinder mUnbinder;
    private View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setContentView(), null);
        return mView;
    }

    abstract protected int setContentView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initInject();
        initViewAndEvent();
    }

    abstract protected void initInject();

    abstract protected void initViewAndEvent();

    private AppComponent getAppComponent(){
        return ((App)_mActivity.getApplication()).getAppComponent();
    }

    protected FragmentComponent getFragmentComponent(){
        return  DaggerFragmentComponent.builder()
                .appComponent(getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
