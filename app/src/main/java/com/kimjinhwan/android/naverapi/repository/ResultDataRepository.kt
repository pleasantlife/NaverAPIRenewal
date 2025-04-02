package com.kimjinhwan.android.naverapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kimjinhwan.android.naverapi.APIService
import com.kimjinhwan.android.naverapi.ResultDataFactory
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
//        return Transformations.switchMap(
//            resultDataFactory.resultDataSource, ResultDataSource::lowestPrice
//        )
        return resultDataFactory.resultDataSource.switchMap { data ->
            data.lowestPrice
        }
    }

    fun getNetworkState(): LiveData<String> {
        return resultDataFactory.resultDataSource.switchMap { data ->
            data.networkState
        }
//        return Transformations.switchMap(
//            resultDataFactory.resultDataSource, ResultDataSource::networkState
//        )
    }
}