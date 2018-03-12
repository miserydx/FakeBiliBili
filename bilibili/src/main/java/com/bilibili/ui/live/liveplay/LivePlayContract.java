package com.bilibili.ui.live.liveplay;

import com.bilibili.model.bean.live.LiveIndex;
import com.common.base.BasePresenter;
import com.common.base.BaseView;

/**
 * Created by miserydx on 17/12/20.
 */

public interface LivePlayContract {

    interface View extends BaseView {

        void onInfoPrepared(LiveIndex liveIndex);

    }

    interface Presenter extends BasePresenter {

        void getLiveIndex(int roomId);

    }

}
