package com.kimjinhwan.android.naverapi.repository

import android.app.Application
import android.util.Log
import com.kimjinhwan.android.naverapi.dao.ProductDao
import com.kimjinhwan.android.naverapi.database.AppDatabase
import com.kimjinhwan.android.naverapi.model.ResultItem
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailViewModelRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private val appDatabase = AppDatabase.getInstance(application)
    private val productDao: ProductDao = appDatabase.productDao()

    fun insertItem(resultItem: ResultItem) {
        launch {
            if (productDao.findItem(resultItem.productId) <= 0) {
                productDao.insert(resultItem)
            }
        }
    }

    fun deleteItem(resultItem: ResultItem) {
        launch {
            productDao.delete(resultItem)
        }
    }

    fun findItem(resultItem: ResultItem): Int {
        var value: Int
        runBlocking(coroutineContext) {
            value = productDao.findItem(resultItem.productId)
        }
        return value
    }

}