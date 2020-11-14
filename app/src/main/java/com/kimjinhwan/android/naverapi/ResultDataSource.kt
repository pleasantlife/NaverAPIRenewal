package com.kimjinhwan.android.naverapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.kimjinhwan.android.naverapi.model.ResultItem
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultDataSource @Inject constructor(private val apiService: APIService,
                                           private val query: String): PositionalDataSource<ResultItem>() {

    companion object {
        val PAGED_SIZE = 100
    }

    private var startValue = 1
    val lowestPrice = MutableLiveData<Long>()
    val networkState = MutableLiveData<String>()
    private var price = Long.MAX_VALUE

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ResultItem>) {
        networkState.postValue("LOADING")
        startValue = 1
        lowestPrice.postValue(Long.MAX_VALUE)
        price = Long.MAX_VALUE
        Log.e("price::", "${price}")
        apiService.getResult(query, PAGED_SIZE, startValue).subscribeOn(Schedulers.io()).subscribe({
            it.items.forEach{ item ->
                if(item.lprice < price) {
                    price = item.lprice
                }
                lowestPrice.postValue(price)
            }

            Log.e("getValue1::", "${lowestPrice.value}")
            Log.e("pageInit::", "${it.start}")
            callback.onResult(it.items, 0, it.total)
            networkState.postValue("IDLE")
        },{
            networkState.postValue("ERROR")
        })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ResultItem>) {
        networkState.postValue("LOADING")
        startValue += PAGED_SIZE
        if(startValue <= 1000) {
            apiService.getResult(query, PAGED_SIZE, startValue).subscribeOn(Schedulers.io())
                .subscribe({
                    it.items.forEach{ item ->
                        if(item.lprice < price) {
                            price = item.lprice
                            Log.e("hihihi!:::", "${price}")
                        }
                        lowestPrice.postValue(price)
                    }
                    callback.onResult(it.items)
                    networkState.postValue("IDLE")
                }, {
                    networkState.postValue("ERROR")
                })
        }
    }
}