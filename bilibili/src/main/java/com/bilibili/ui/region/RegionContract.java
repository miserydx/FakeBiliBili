package com.bilibili.ui.region;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public interface RegionContract {

    interface View extends BaseView {

        void onDataUpdated(Items items);

        void showLoadFailed();
    }

    interface Presenter extends BasePresenter {

    }
}
