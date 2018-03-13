package com.bilibili.ui.test.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.adapter.NewsItemViewBinder;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.ui.test.mvp.presenter.MvpStructurePresenter;
import com.bilibili.widget.adapter.CommonAdapter;
import com.common.base.IBaseMvpActivity;
import com.common.base.BaseActivity;
import com.common.base.adapter.OnItemClickListener;
import com.common.util.StatusBarUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;

public class StatusWithPictureActivity extends BaseActivity implements IBaseMvpActivity<MvpStructurePresenter>, MvpStructureContract.View {

    private final String TAG = StatusWithPictureActivity.class.getSimpleName();

    @Inject
    MvpStructurePresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_srl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.activity_main_rv)
    RecyclerView recyclerView;

//    private MvpStructureAdapter mAdapter;

    private CommonAdapter mAdapter;

    private Items mList = new Items();

    @Override
    public int getLayoutId() {
        return R.layout.activity_status_picture;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public MvpStructurePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViewAndEvent() {
        //自定义statusbar样式,与toolbar融合
        StatusBarUtil.setStatusBarMergeWithTopView(this, mToolbar);
        mToolbar.setTitle("新闻");
        setSupportActionBar(mToolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new MvpStructureAdapter(this, mList);
//        recyclerView.setAdapter(mAdapter);

        mAdapter = new CommonAdapter();
        mAdapter.register(WeiXinJingXuanBean.NewsList.class, new NewsItemViewBinder(this));
        recyclerView.setAdapter(mAdapter);
        mPresenter.loadData();
        recyclerView.addOnItemTouchListener(new OnItemClickListener(recyclerView) {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(StatusWithPictureActivity.this, "onItemClick position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
                Toast.makeText(StatusWithPictureActivity.this, "onItemLongClick position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.setItems(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

}
