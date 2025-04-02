package com.kimjinhwan.android.naverapi

import com.kimjinhwan.android.naverapi.model.Result
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    @Headers(
        "X-Naver-Client-Id: ${BuildConfig.NAVER_CLIENT_ID}",
        "X-Naver-Client-Secret: ${BuildConfig.NAVER_CLIENT_SECRET}"
    )
    @GET("shop.json")
    fun getResult(@Query("query") query: String,
                  @Query("display") display: Int,
                  @Query("start") start: Int): Single<Result>
}