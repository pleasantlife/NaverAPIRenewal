package com.kimjinhwan.android.naverapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kimjinhwan.android.naverapi.dao.ProductDao
import com.kimjinhwan.android.naverapi.database.AppDatabase
import com.kimjinhwan.android.naverapi.model.ResultItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoriteListRepository(application: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private val appDatabase = AppDatabase.getInstance(application)
    private val productDao: ProductDao = appDatabase.productDao()

    val itemList: LiveData<List<ResultItem>> by lazy {
        productDao.getAll()
    }

    fun deleteAll() {
        launch {
            productDao.deleteAll()
        }
    }
}