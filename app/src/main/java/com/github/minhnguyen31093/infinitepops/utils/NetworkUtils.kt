package com.github.minhnguyen31093.infinitepops.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val activeNetwork = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false
    }
}