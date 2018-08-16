package com.github.minhnguyen31093.infinitepops.api

import com.github.minhnguyen31093.infinitepops.model.HomeFeed
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable

object HomeFeedApi {

    fun getDetail(): Observable<HomeFeed?> {
        return Rx2AndroidNetworking.get("https://api.popjam.com/v2/users/2e009fcf-d63c-4a3f-92f4-e847e2d5eee8/homeFeed")
                .build().getObjectObservable(HomeFeed::class.java)
    }

    fun getMore(id: String): Observable<HomeFeed?> {
        return Rx2AndroidNetworking.get("https://api.popjam.com/v2/users/2e009fcf-d63c-4a3f-92f4-e847e2d5eee8/homeFeed")
                .addQueryParameter("lastId", id)
                .build().getObjectObservable(HomeFeed::class.java)
    }
}