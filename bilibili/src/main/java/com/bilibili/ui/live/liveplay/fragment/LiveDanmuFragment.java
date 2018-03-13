package com.bilibili.ui.live.liveplay.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;

import com.bilibili.R;
import com.bilibili.ui.live.liveplay.LiveDanMuCallback;
import com.bilibili.ui.live.liveplay.fragment.binder.LiveDanmuItemBinder;
import com.bilibili.widget.danmu.live.LiveDanMuReceiver;
import com.bilibili.widget.danmu.live.entity.DanMuMSGEntity;
import com.bilibili.widget.adapter.CommonAdapter;
import com.common.base.BaseFragment;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miserydx on 17/12/20.
 */

public class LiveDanmuFragment extends BaseFragment {

    private LinkedList<String> danmuQueue = new LinkedList<>();

    public static LiveDanmuFragment newInstance() {
        LiveDanmuFragment fragment = new LiveDanmuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;
    private LiveDanMuCallback liveDanmuFragmentCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_danmu;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {
        mAdapter = new CommonAdapter();
        //register item
        mAdapter.register(String.class, new LiveDanmuItemBinder());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setAdapter(mAdapter);
        connectLiveDanmu();
    }

    /**
     * 连接弹幕
     */
    private void connectLiveDanmu() {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (!danmuQueue.isEmpty() && mRecyclerView != null) {
                            mRecyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.addItem(danmuQueue.poll());
                                    mRecyclerView.getLayoutManager().scrollToPosition(mAdapter.getItemCount() - 1);
                                }
                            });
                        }
                    }
                });
        liveDanmuFragmentCallback = new LiveDanMuCallback() {
            @Override
            public void onDanMuMSGPackage(final DanMuMSGEntity danMuMSGEntity) {
                super.onDanMuMSGPackage(danMuMSGEntity);
                String string = danMuMSGEntity.getSenderNick() + "：" + danMuMSGEntity.getDanMuContent();
                danmuQueue.add(string);
            }
        };
        LiveDanMuReceiver.getInstance()
                .addCallback(liveDanmuFragmentCallback);
    }

    @Override
    public void onDestroyView() {
        LiveDanMuReceiver.getInstance().removeCallback(liveDanmuFragmentCallback);
        super.onDestroyView();
    }
}
