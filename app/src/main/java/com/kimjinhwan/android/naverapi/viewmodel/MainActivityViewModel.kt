package com.kimjinhwan.android.naverapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.kimjinhwan.android.naverapi.ResultDataRepository
import com.kimjinhwan.android.naverapi.ResultItem
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val resultDataRepository: ResultDataRepository) {

    private val queryLiveData = MutableLiveData<String>()
    fun setQuery(query: String) {
        queryLiveData.postValue(query)
    }

    val searchResult: LiveData<PagedList<ResultItem>> by lazy {
        Transformations.switchMap(queryLiveData) {
            resultDataRepository.getResult(it)
        }
    }

    val lowestPrice: LiveData<Long> by lazy {
        resultDataRepository.getLowestPrice()
    }
}