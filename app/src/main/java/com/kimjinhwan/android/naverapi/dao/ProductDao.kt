package com.kimjinhwan.android.naverapi.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kimjinhwan.android.naverapi.model.ResultItem

@Dao
interface ProductDao {

    @Query("SELECT * FROM resultItem ORDER BY id ASC")
    fun getAll(): LiveData<List<ResultItem>>

    @Query("SELECT * FROM resultItem WHERE productId = :productId")
    fun findItem(productId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(resultItem: ResultItem)

    @Delete
    fun delete(resultItem: ResultItem)

    @Query("DELETE FROM resultItem")
    fun deleteAll()
}