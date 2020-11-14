package com.kimjinhwan.android.naverapi.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kimjinhwan.android.naverapi.repository.DetailViewModelRepository
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.model.ResultItem
import com.kimjinhwan.android.naverapi.viewmodel.DetailActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.DetailViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var viewModel: DetailActivityViewModel
    private var isSavedItem: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val viewModelRepository =
            DetailViewModelRepository(
                application
            )
        viewModel = ViewModelProvider(this,
            DetailViewModelFactory(
                viewModelRepository
            )
        ).get(DetailActivityViewModel::class.java)

        val item = intent.getParcelableExtra<ResultItem>("item")

        if (item != null) {
            isSavedItem = viewModel.checkSavedItem(item) > 0
            setViewFromItem(item)

        }


        btnSave.setOnClickListener {
            if(isSavedItem) {
                viewModel.deleteItem(item!!)
                Snackbar.make(detailLayout, getString(R.string.remove_complete), Snackbar.LENGTH_SHORT).show()
                onBackPressed()
            } else {
                viewModel.insertItem(item!!)
                Snackbar.make(detailLayout, getString(R.string.insert_complete), Snackbar.LENGTH_SHORT).show()
                isSavedItem = true
                setSaveBtnTxt()
            }

        }
    }

    private fun setViewFromItem(item: ResultItem) {
        Glide.with(this).load(item.image).into(detailImage)
        mallNameTxt.text = item.mallName
        productTxt.text = item.title.replace("<b>","").replace("</b>","")
        val formattedPrice = NumberFormat.getInstance(Locale.getDefault()).format(item.lprice)
        lowestPriceTxt.text = getString(R.string.won, formattedPrice)
        btnMove.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.link)
            startActivity(intent)
        }

        setSaveBtnTxt()


    }

    private fun setSaveBtnTxt(){
        if(isSavedItem){
            btnSave.text = getString(R.string.remove)
        } else {
            btnSave.text = getString(R.string.save)
        }
    }
}