package com.kimjinhwan.android.naverapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimjinhwan.android.naverapi.viewmodel.MainActivityViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private val resultRecyclerAdapter = ResultRecyclerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = resultRecyclerAdapter
        }

        viewModel.searchResult.observe(this, Observer {
            resultRecyclerAdapter.submitList(it)
            initPrice()
        })



        queryEditTxt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    checkSearch()
                    true
                }
                else -> false
            }
        }

        searchBtn.setOnClickListener{
            checkSearch()
        }
    }

    private fun initPrice() {
        viewModel.lowestPrice.observe(this, Observer { lowestPrice ->
            lowestPriceTxt.text = lowestPrice.toString()
            resultRecyclerAdapter.setLowestPrice(lowestPrice)
        })
    }

    private fun checkSearch(){
        val query = queryEditTxt.text.toString()
        if(query.isNotEmpty()) {
            hideKeyboard()
            viewModel.setQuery(queryEditTxt.text.toString())

        } else {
            Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(queryEditTxt.windowToken, 0)
    }
}