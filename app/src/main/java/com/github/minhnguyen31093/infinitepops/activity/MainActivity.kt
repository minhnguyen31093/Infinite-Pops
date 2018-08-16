package com.github.minhnguyen31093.infinitepops.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.github.minhnguyen31093.infinitepops.R
import com.github.minhnguyen31093.infinitepops.adapter.HomeFeedAdapter
import com.github.minhnguyen31093.infinitepops.base.BaseActivity
import com.github.minhnguyen31093.infinitepops.model.HomeFeed
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), MainView {

    private var exitApp: Int = 0
    private lateinit var mPresenter: MainPresenter
    private var adapter: HomeFeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    override fun onStart() {
        super.onStart()
        mPresenter = MainPresenter(this, this)
        pbLoading.visibility = View.VISIBLE
        mPresenter.getList()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onDetach()
    }

    override fun onBackPressed() {
        if (exitApp == 1) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Pressed back again to exit!", Toast.LENGTH_SHORT).show()
            exitApp = 1
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    exitApp = 0
                }
            }, 3000)
        }
    }

    override fun setList(homeFeed: HomeFeed?) {
        pbLoading.visibility = View.GONE
        if (homeFeed?.feedItems != null) {
            if (adapter == null) {
                adapter = HomeFeedAdapter(rvMain, homeFeed.feedItems)
                adapter!!.onLoadMore {
                    val count = adapter!!.getRealCount()
                    if (count > 0) {
                        val item = adapter!!.getItem(count - 1)
                        if (item != null) {
                            mPresenter.loadMore(item.id)
                        }
                    }
                }
                rvMain.adapter = adapter
            }
        }
    }

    override fun addMore(homeFeed: HomeFeed?) {
        if (homeFeed != null) {
            if (adapter != null) {
                adapter!!.insert(homeFeed.feedItems)
            }
        }
        if (homeFeed?.feedItems == null || homeFeed.feedItems!!.isEmpty()) {
            if (adapter != null) {
                adapter!!.isCompleted = true
            }
        }
    }

    override fun onListFailed() {
        pbLoading.visibility = View.GONE
    }

    override fun onMoreFailed() {
        if (adapter != null) {
            adapter!!.isCompleted = true
        }
    }

    private fun setUpView() {
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
