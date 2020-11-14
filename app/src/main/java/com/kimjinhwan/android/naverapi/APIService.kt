package com.kimjinhwan.android.naverapi

import com.kimjinhwan.android.naverapi.model.Result
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    @Headers(
        "X-Naver-Client-Id: OzYyCwp8a0JpBJiKXycC",
        "X-Naver-Client-Secret: SszZOHXjYS"
    )
    @GET("shop.json")
    fun getResult(@Query("query") query: String,
                  @Query("display") display: Int,
                  @Query("start") start: Int): Single<Result>
}