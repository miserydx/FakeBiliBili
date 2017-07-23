package com.bilibili.ui.bangumi;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/6/29.
 */

public interface BangumiContract {

    interface View extends BaseView {

        void onDataUpdated(Items items, int state);

        void onRefreshingStateChanged(boolean isRefresh);

    }

    interface Presenter extends BasePresenter {

        void pullToRefresh();

        void loadMore();

    }
}
