package com.kimjinhwan.android.naverapi.di

import com.kimjinhwan.android.naverapi.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun getOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.HEADERS)).build()
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): APIService {
        val baseUrl = "https://openapi.naver.com/v1/search/"

        return Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(APIService::class.java)
    }

}