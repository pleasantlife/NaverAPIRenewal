package com.kimjinhwan.android.naverapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimjinhwan.android.naverapi.adapter.FavoriteAdapter
import com.kimjinhwan.android.naverapi.repository.FavoriteListRepository
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.viewmodel.FavoriteListActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.FavoriteListViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoriteListActivity : AppCompatActivity() {

    lateinit var viewModel: FavoriteListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val favoriteListRepository =
            FavoriteListRepository(
                application
            )
        val viewModelFactory =
            FavoriteListViewModelFactory(
                favoriteListRepository
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteListActivityViewModel::class.java)

        val favoriteAdapter =
            FavoriteAdapter(this)
        favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            adapter = favoriteAdapter
        }

        clearAll.setOnClickListener {
            viewModel.clearAll()
        }

        viewModel.itemList.observe(this, Observer {
            favoriteAdapter.setListData(it)
            if(it.isNotEmpty()) {
                emptyTxt.visibility = View.GONE
            } else {
                emptyTxt.visibility = View.VISIBLE
            }
        })
    }
}