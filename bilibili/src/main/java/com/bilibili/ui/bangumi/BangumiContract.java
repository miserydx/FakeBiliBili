package com.bilibili.ui.bangumi;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/6/29.
 */

public interface BangumiContract {

    interface View extends BaseView {

        void updateData(Items items);

    }

    interface Presenter extends BasePresenter {

    }
}
