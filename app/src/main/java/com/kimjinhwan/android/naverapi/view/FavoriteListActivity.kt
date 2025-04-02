package com.kimjinhwan.android.naverapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kimjinhwan.android.naverapi.adapter.FavoriteAdapter
import com.kimjinhwan.android.naverapi.repository.FavoriteListRepository
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.databinding.ActivityFavoriteListBinding
import com.kimjinhwan.android.naverapi.viewmodel.FavoriteListActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.FavoriteListViewModelFactory

class FavoriteListActivity : AppCompatActivity() {

    lateinit var viewModel: FavoriteListActivityViewModel
    var savedItemSize: Int = 0
    private lateinit var binding: ActivityFavoriteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupWindowInsets()
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            adapter = favoriteAdapter
        }

        binding.clearAll.setOnClickListener {
            if(savedItemSize > 0) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.remove_all)
                    .setNegativeButton("Yes") { _, _ -> viewModel.clearAll() }
                    .setPositiveButton("No") { dialog, _ -> dialog.dismiss() }.show()
            } else {
                Snackbar.make(binding.favoriteLayout, getString(R.string.no_item), Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.itemList.observe(this, Observer {
            savedItemSize = it.size
            favoriteAdapter.setListData(it)
            if(it.isEmpty()){
                binding.emptyTxt.visibility = View.VISIBLE
            } else {
                binding.emptyTxt.visibility = View.GONE
            }
        })
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) {
                view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}