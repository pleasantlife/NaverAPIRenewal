package com.kimjinhwan.android.naverapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kimjinhwan.android.naverapi.APIService
import com.kimjinhwan.android.naverapi.ResultDataFactory
import com.kimjinhwan.android.naverapi.ResultDataSource
import com.kimjinhwan.android.naverapi.ResultDataSource.Companion.PAGED_SIZE
import com.kimjinhwan.android.naverapi.model.ResultItem
import javax.inject.Inject

class ResultDataRepository @Inject constructor(private val apiService: APIService) {

    lateinit var resultDataFactory: ResultDataFactory

    fun getResult(query: String): LiveData<PagedList<ResultItem>> {
        resultDataFactory =
            ResultDataFactory(apiService, query)
        val config = PagedList.Config.Builder()
            .setPageSize(PAGED_SIZE)
            .setInitialLoadSizeHint(PAGED_SIZE).build()

        return LivePagedListBuilder(resultDataFactory, config).build()
    }

    fun getLowestPrice(): LiveData<Long> {
        return Transformations.switchMap(
            resultDataFactory.resultDataSource, ResultDataSource::lowestPrice
        )
    }

    fun getNetworkState(): LiveData<String> {
        return Transformations.switchMap(
            resultDataFactory.resultDataSource, ResultDataSource::networkState
        )
    }
}