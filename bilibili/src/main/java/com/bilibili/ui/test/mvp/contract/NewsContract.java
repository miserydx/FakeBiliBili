package com.bilibili.ui.test.mvp.contract;

import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.common.base.BasePresenter;
import com.common.base.BaseView;

import java.util.List;

/**
 * Created by jiayiyang on 17/3/25.
 */

public interface NewsContract {

    interface View extends BaseView {

        void updateData(List<WeiXinJingXuanBean.NewsList> list);

        void setRefreshing();

        void onLoadMore(List<WeiXinJingXuanBean.NewsList> list);

    }

    interface Presenter extends BasePresenter {

        void loadMore();

    }

}
