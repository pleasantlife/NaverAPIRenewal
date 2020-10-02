package com.kimjinhwan.android.naverapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultItem(

    val title: String,
    val link: String,
    val image: String,
    val lprice: Long,
    val hprice: Long,
    val mallName: String,
    val productId: String,
    val productType: String,
    val brand: String,
    val maker: String,
    val category1: String,
    val category2: String,
    val category3: String,
    val category4: String

) : Parcelable