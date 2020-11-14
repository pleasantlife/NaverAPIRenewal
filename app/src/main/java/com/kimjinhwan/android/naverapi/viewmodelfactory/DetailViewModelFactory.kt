package com.kimjinhwan.android.naverapi.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimjinhwan.android.naverapi.repository.DetailViewModelRepository
import com.kimjinhwan.android.naverapi.viewmodel.DetailActivityViewModel

class DetailViewModelFactory(private val detailViewModelRepository: DetailViewModelRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(
            detailViewModelRepository
        ) as T
    }
}