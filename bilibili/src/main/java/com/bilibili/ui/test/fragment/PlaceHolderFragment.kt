package com.bilibili.ui.test.fragment

import android.widget.TextView
import butterknife.BindView
import com.bilibili.R
import com.common.base.BaseFragment
import java.util.*

/**
 * Created by miserydx on 17/10/26.
 */
class PlaceHolderFragment : BaseFragment() {

    @BindView(R.id.tv_place_holder)
    lateinit var tvPlaceHolder: TextView

    override fun getLayoutId(): Int = R.layout.fragment_placeholder

    override fun initInject() {

    }

    override fun initViewAndEvent() {
        tvPlaceHolder.text = Random().nextInt(2345).toString()
    }
}