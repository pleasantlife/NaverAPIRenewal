package com.kimjinhwan.android.naverapi

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

    fun resetPrice() {
        price = Long.MAX_VALUE
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ResultItem>) {
        networkState.postValue("LOADING")
        startValue = 1
        lowestPrice.postValue(Long.MAX_VALUE)
        resetPrice()
        apiService.getResult(query, PAGED_SIZE, startValue).subscribeOn(Schedulers.io()).subscribe({
            it.items.forEach{ item ->
                if(item.lprice.toLong() < price) {
                    price = item.lprice.toLong()
                }
                lowestPrice.postValue(price)
            }
            callback.onResult(it.items, 0, it.total)
            if(it.items.isNotEmpty()) {
                networkState.postValue("IDLE")
            } else {
                networkState.postValue("EMPTY")
            }
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
                        if(item.lprice.toLong() < price) {
                            price = item.lprice.toLong()
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