package com.kimjinhwan.android.naverapi.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kimjinhwan.android.naverapi.*
import com.kimjinhwan.android.naverapi.adapter.ResultRecyclerAdapter
import com.kimjinhwan.android.naverapi.repository.ResultDataRepository
import com.kimjinhwan.android.naverapi.viewmodel.MainActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: APIService
    @Inject
    lateinit var resultDataRepository: ResultDataRepository
    private lateinit var viewModel: MainActivityViewModel
    private var resultRecyclerAdapter =
        ResultRecyclerAdapter(this)

    private var pressedTime: Long = 0
    private var seconds: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        lowestPriceTxt.text = getString(R.string.won, "0")

        setViewModelProvider()

        resultRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = resultRecyclerAdapter
        }



        viewModel.searchResult.observe(this, Observer {
                resultRecyclerAdapter.submitList(it)
                setLowestPrice()
                resultRecyclerView.scheduleLayoutAnimation()
        })

        searchBtn.setOnClickListener {
            checkSearch()
        }

        menuBtn.setOnClickListener {
            showPopup(it)
        }

        clearTxtBtn.setOnClickListener {
            queryEditTxt.text.clear()
        }

        viewModel.networkState.observe(this, Observer {
            val snackBar = Snackbar.make(mainLayout, getString(R.string.load_more), Snackbar.LENGTH_SHORT)
            when (it) {
                "ERROR" -> {
                    if(snackBar.isShown){
                        snackBar.dismiss()
                    }
                }
                "LOADING" -> {
                    if(!snackBar.isShown){
                        snackBar.show()
                    }
                }
                "EMPTY" -> {
                    lowestPriceTxt.text = getString(R.string.won, "0")
                    if(snackBar.isShown) {
                        snackBar.dismiss()
                    }
                }
                else -> {
                    if(snackBar.isShown){
                        snackBar.dismiss()
                    }
                }
            }
        })

        queryEditTxt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    checkSearch()
                    true
                }
                else -> {
                    if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                        checkSearch()
                        true
                    } else {
                        false
                    }
                }
            }
        }

        setTextChangeListener()
    }

    private fun setViewModelProvider(){
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(
                resultDataRepository
            )
        )[MainActivityViewModel::class.java]
    }

    private fun showPopup(v : View){
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_main, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.saveItem-> {
                    startActivity(Intent(this, FavoriteListActivity::class.java))
                }
                R.id.openSource-> {
                    startActivity(Intent(this, OpenSourceActivity::class.java))
                }
            }
            true
        }
        popup.show()
    }

    private fun setLowestPrice() {
        viewModel.lowestPrice.observe(this, Observer { lowestPrice ->
            if(lowestPrice < Long.MAX_VALUE) {
                val formattedPrice = NumberFormat.getInstance(Locale.getDefault()).format(lowestPrice)
                lowestPriceTxt.text = getString(R.string.won, formattedPrice)
            }
            resultRecyclerAdapter.setLowestPrice(lowestPrice)
        })
    }

    private fun checkSearch(){
        val query = queryEditTxt.text.toString()
        if(query.isNotEmpty()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(queryEditTxt.windowToken, 0)
            if(viewModel.networkState.value != "LOADING") {
                viewModel.setQuery(queryEditTxt.text.toString())
            }
        } else {
            Snackbar.make(mainLayout, getString(R.string.empty_keyword), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setTextChangeListener() {
        queryEditTxt.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    clearTxtBtn.visibility = View.GONE
                } else {
                    clearTxtBtn.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun onBackPressed() {
        val backPressedSnackBar = Snackbar.make(mainLayout, getString(R.string.backpressed_message), Snackbar.LENGTH_SHORT)
        if(pressedTime == 0L){
            if(!backPressedSnackBar.isShown){
                backPressedSnackBar.show()
            }
            pressedTime = System.currentTimeMillis()
        } else {
            seconds = System.currentTimeMillis() - pressedTime

            if (seconds > 3000) {
                if(!backPressedSnackBar.isShown){
                    backPressedSnackBar.show()
                }
                pressedTime = 0
            } else {
                super.onBackPressed()
                finish()
            }
        }
    }
}