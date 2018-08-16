package com.github.minhnguyen31093.infinitepops.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : BaseView>(var context: Context?, var view: V) {

    var compositeDisposable = CompositeDisposable()

    fun onDetach() {
        compositeDisposable.dispose()
    }

    fun addDisposable(d: Disposable?) {
        if (d != null) {
            compositeDisposable.add(d)
        }
    }

    fun removeDisposable(d: Disposable?) {
        if (d != null) {
            compositeDisposable.remove(d)
        }
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}