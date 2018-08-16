package com.github.minhnguyen31093.infinitepops.activity

import com.github.minhnguyen31093.infinitepops.base.BaseView
import com.github.minhnguyen31093.infinitepops.model.HomeFeed

interface MainView : BaseView {
    fun setList(homeFeed: HomeFeed?)
    fun addMore(homeFeed: HomeFeed?)
    fun onListFailed()
    fun onMoreFailed()
}