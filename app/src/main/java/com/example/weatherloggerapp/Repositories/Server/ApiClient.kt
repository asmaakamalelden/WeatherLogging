package com.example.weatherloggerapp.Repositories.Server

import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    var BASEURL = "https://api.openweathermap.org"
    private var retrofit: Retrofit? = null
    private val APP_ID_KEY = "appid"
    private val APP_ID_VALUE = "4ba065013c8f39d66a92311c5e84e33e"

    private fun buildClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                val url =
                    request.url().newBuilder().addQueryParameter(APP_ID_KEY, APP_ID_VALUE)
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(buildClient())
                .baseUrl(BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }


    companion object {
        fun getInstance(): ApiClient? {
            var mInstance: ApiClient? = null
            if (mInstance == null) {
                mInstance = ApiClient()
            }
            return mInstance
        }
    }
    val getApi by lazy {
       getClientApi()
    }

    fun getClientApi(): ApiInterface? {
        return getClient()!!.create(ApiInterface::class.java)
    }

}