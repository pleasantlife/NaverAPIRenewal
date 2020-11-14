package com.kimjinhwan.android.naverapi.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimjinhwan.android.naverapi.repository.FavoriteListRepository
import com.kimjinhwan.android.naverapi.viewmodel.FavoriteListActivityViewModel

class FavoriteListViewModelFactory(private val favoriteListRepository: FavoriteListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteListActivityViewModel(
            favoriteListRepository
        ) as T
    }
}