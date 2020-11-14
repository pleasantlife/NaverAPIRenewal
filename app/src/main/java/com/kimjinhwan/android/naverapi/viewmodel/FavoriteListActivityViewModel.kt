package com.kimjinhwan.android.naverapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kimjinhwan.android.naverapi.repository.FavoriteListRepository
import com.kimjinhwan.android.naverapi.model.ResultItem
import javax.inject.Inject

class FavoriteListActivityViewModel @Inject constructor(private val favoriteListRepository: FavoriteListRepository): ViewModel() {

    val itemList: LiveData<List<ResultItem>> by lazy {
        favoriteListRepository.itemList
    }

    fun clearAll() {
        favoriteListRepository.deleteAll()
    }
}