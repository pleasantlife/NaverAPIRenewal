package com.kimjinhwan.android.naverapi

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    @Headers(
        "X-Naver-Client-Id: AAAAAAA",
        "X-Naver-Client-Secret: *******"
    )
    @GET("shop.json")
    fun getResult(@Query("query") query: String,
                  @Query("display") display: Int,
                  @Query("start") start: Int): Single<Result>
}