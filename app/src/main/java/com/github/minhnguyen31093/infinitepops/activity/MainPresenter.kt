package com.github.minhnguyen31093.infinitepops.activity

import android.content.Context
import android.widget.Toast
import com.github.minhnguyen31093.infinitepops.api.HomeFeedApi
import com.github.minhnguyen31093.infinitepops.base.BasePresenter
import com.github.minhnguyen31093.infinitepops.utils.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(context: Context?, view: MainView) : BasePresenter<MainView>(context, view) {

    fun getList() {
        if (NetworkUtils.isNetworkConnected(context)) {
            addDisposable(HomeFeedApi.getDetail().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.setList(it)
                    }, {
                        Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show()
                        view.onListFailed()
                    }))
        } else {
            Toast.makeText(context, "No Network connection!", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadMore(id: String?) {
        if (NetworkUtils.isNetworkConnected(context)) {
            if (id != null && id.isNotEmpty()) {
                addDisposable(HomeFeedApi.getMore(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.addMore(it)
                        }, {
                            Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show()
                            view.onMoreFailed()
                        }))
            }
        } else {
            Toast.makeText(context, "No Network connection!", Toast.LENGTH_SHORT).show()
        }
    }
}