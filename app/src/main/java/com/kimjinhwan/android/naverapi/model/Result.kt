package com.kimjinhwan.android.naverapi.model

data class Result(

    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<ResultItem>

)