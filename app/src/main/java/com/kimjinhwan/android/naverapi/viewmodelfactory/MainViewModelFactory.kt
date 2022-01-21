package com.kimjinhwan.android.naverapi.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimjinhwan.android.naverapi.repository.ResultDataRepository
import com.kimjinhwan.android.naverapi.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val resultDataRepository: ResultDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            resultDataRepository
        ) as T
    }
}