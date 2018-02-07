package com.bilibili.ui.live.liveplay.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.R;
import com.bilibili.ui.live.liveplay.LiveDanMuCallback;
import com.bilibili.ui.live.liveplay.fragment.binder.LiveDanmuItemBinder;
import com.bilibili.util.CommonConsumer;
import com.bilibili.util.RxJavaUtil;
import com.bilibili.widget.danmu.live.LiveDanMuReceiver;
import com.bilibili.widget.danmu.live.entity.DanMuMSGEntity;
import com.bilibili.widget.adapter.CommonAdapter;
import com.common.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by miserydx on 17/12/20.
 */

public class LiveDanmuFragment extends BaseFragment {

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
        mRecyclerView.setAdapter(mAdapter);
        connectLiveDanmu();
    }

    /**
     * 连接弹幕
     */
    private void connectLiveDanmu() {
        liveDanmuFragmentCallback = new LiveDanMuCallback() {
            @Override
            public void onDanMuMSGPackage(final DanMuMSGEntity danMuMSGEntity) {
                super.onDanMuMSGPackage(danMuMSGEntity);
                RxJavaUtil.runOnUiThread(new CommonConsumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String string = danMuMSGEntity.getSenderNick() + "：" + danMuMSGEntity.getDanMuContent();
                        mAdapter.addItem(string);
                        if (mRecyclerView != null) {
                            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                        }
                    }
                });
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
