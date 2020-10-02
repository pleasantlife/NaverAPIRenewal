package com.kimjinhwan.android.naverapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PositionalDataSource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultDataSource @Inject constructor(private val apiService: APIService,
                                           private val query: String): PositionalDataSource<ResultItem>() {

    companion object {
        val PAGED_SIZE = 100
    }

    private var startValue = 1
    val lowestPrice = MutableLiveData<Long>()
    private var price = Long.MAX_VALUE

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ResultItem>) {
        startValue = 1
        lowestPrice.postValue(Long.MAX_VALUE)
        price = Long.MAX_VALUE
        apiService.getResult(query, PAGED_SIZE, startValue).subscribeOn(Schedulers.io()).subscribe({
            it.items.forEach{ item ->
                if(item.lprice < price) {
                    price = item.lprice
                }
            }
            lowestPrice.postValue(price)
            Log.e("pageInit::", "${it.start}")
            callback.onResult(it.items, 0, it.total)
        },{})
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ResultItem>) {
        startValue += 1
        if(startValue <= 1000) {
            apiService.getResult(query, PAGED_SIZE, startValue).subscribeOn(Schedulers.io())
                .subscribe({
                    it.items.forEach{ item ->
                        if(item.lprice < price) {
                            price = item.lprice
                        }
                    }
                    Log.e("page::", "${it.start}")
                    lowestPrice.postValue(price)
                    callback.onResult(it.items)
                }, {})
        }
    }

}