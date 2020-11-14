package com.kimjinhwan.android.naverapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kimjinhwan.android.naverapi.repository.ResultDataRepository
import com.kimjinhwan.android.naverapi.model.ResultItem
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val resultDataRepository: ResultDataRepository): ViewModel() {

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
        Transformations.switchMap(queryLiveData) {
            resultDataRepository.getLowestPrice()
        }
    }

    val networkState: LiveData<String> by lazy {
        Transformations.switchMap(queryLiveData) {
            resultDataRepository.getNetworkState()
        }
    }
}