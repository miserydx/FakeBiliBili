package com.bilibili.ui.test.mvp.contract;

import com.bilibili.base.BasePresenter;
import com.bilibili.base.BaseView;
import com.bilibili.model.bean.WeiXinJingXuanBean;

import java.util.List;

/**
 * Created by jiayiyang on 17/3/25.
 */

public interface MvpStructureContract {

    interface View extends BaseView {

        void updateData(List<WeiXinJingXuanBean.NewsList> list);

        void setRefreshing();

    }

    interface Presenter extends BasePresenter {

    }

}
