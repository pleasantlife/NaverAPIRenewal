package com.kimjinhwan.android.naverapi.viewmodel

import androidx.lifecycle.*
import com.kimjinhwan.android.naverapi.repository.DetailViewModelRepository
import com.kimjinhwan.android.naverapi.model.ResultItem
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivityViewModel @Inject constructor(private val detailViewModelRepository: DetailViewModelRepository): ViewModel() {


    fun insertItem(resultItem: ResultItem) {
        detailViewModelRepository.insertItem(resultItem)
    }

    fun deleteItem(resultItem: ResultItem) {
        detailViewModelRepository.deleteItem(resultItem)
    }

    fun checkSavedItem(resultItem: ResultItem): Int {
        return detailViewModelRepository.findItem(resultItem)
    }

}