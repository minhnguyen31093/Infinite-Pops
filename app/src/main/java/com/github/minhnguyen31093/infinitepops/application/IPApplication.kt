package com.github.minhnguyen31093.infinitepops.application

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class IPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = OkHttpClient().newBuilder()
        val socketFactory = providesSSLSocketFactory()
        if (socketFactory != null) {
            builder.sslSocketFactory(socketFactory, providesX509TrustManager())
        }
        AndroidNetworking.initialize(applicationContext, builder.build())
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
    }

    private fun providesSSLSocketFactory(): SSLSocketFactory? {
        var sslSocketFactory: SSLSocketFactory? = null
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(providesX509TrustManager())
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, null)
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.socketFactory
        } catch (e: NoSuchAlgorithmException) {
            Log.e("Error", e.toString())
        } catch (e: KeyManagementException) {
            Log.e("Error", e.toString())
        }
        return sslSocketFactory
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun providesX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }
}