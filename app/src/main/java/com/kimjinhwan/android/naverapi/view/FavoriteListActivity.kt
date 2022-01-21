package com.kimjinhwan.android.naverapi.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kimjinhwan.android.naverapi.adapter.FavoriteAdapter
import com.kimjinhwan.android.naverapi.repository.FavoriteListRepository
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.viewmodel.FavoriteListActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.FavoriteListViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoriteListActivity : AppCompatActivity() {

    lateinit var viewModel: FavoriteListActivityViewModel
    var savedItemSize: Int = 0

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
            if(savedItemSize > 0) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.remove_all)
                    .setNegativeButton("Yes") { _, _ -> viewModel.clearAll() }
                    .setPositiveButton("No") { dialog, _ -> dialog.dismiss() }.show()
            } else {
                Snackbar.make(favoriteLayout, getString(R.string.no_item), Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.itemList.observe(this, Observer {
            savedItemSize = it.size
            favoriteAdapter.setListData(it)
            if(it.isEmpty()){
                emptyTxt.visibility = View.VISIBLE
            } else {
                emptyTxt.visibility = View.GONE
            }
        })
    }
}