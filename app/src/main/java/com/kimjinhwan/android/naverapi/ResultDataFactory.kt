package com.kimjinhwan.android.naverapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.kimjinhwan.android.naverapi.model.ResultItem
import javax.inject.Inject

class ResultDataFactory @Inject constructor(private val apiService: APIService,
                                            private val query: String): DataSource.Factory<Int, ResultItem>() {

    val resultDataSource = MutableLiveData<ResultDataSource>()
    lateinit var dataSource: ResultDataSource

    override fun create(): DataSource<Int, ResultItem> {
        dataSource = ResultDataSource(apiService, query)
        resultDataSource.postValue(dataSource)
        return dataSource
    }
}