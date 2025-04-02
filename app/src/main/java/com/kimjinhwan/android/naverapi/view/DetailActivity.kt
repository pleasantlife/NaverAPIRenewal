package com.kimjinhwan.android.naverapi.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kimjinhwan.android.naverapi.repository.DetailViewModelRepository
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.databinding.ActivityDetailBinding
import com.kimjinhwan.android.naverapi.model.ResultItem
import com.kimjinhwan.android.naverapi.viewmodel.DetailActivityViewModel
import com.kimjinhwan.android.naverapi.viewmodelfactory.DetailViewModelFactory
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var viewModel: DetailActivityViewModel
    private var isSavedItem: Boolean = false
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupWindowInsets()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


        binding.btnSave.setOnClickListener {
            if(isSavedItem) {
                viewModel.deleteItem(item!!)
                Toast.makeText(this, getString(R.string.remove_complete), Toast.LENGTH_SHORT).show()
                onBackPressed()
            } else {
                viewModel.insertItem(item!!)
                Toast.makeText(this, getString(R.string.insert_complete), Toast.LENGTH_SHORT).show()
                isSavedItem = true
                setSaveBtnTxt()
            }

        }
    }

    private fun setViewFromItem(item: ResultItem) {
        Glide.with(this).load(item.image).into(binding.detailImage)
        binding.mallNameTxt.text = item.mallName
        binding.productTxt.text = item.title.replace("<b>","").replace("</b>","")
        val formattedPrice = NumberFormat.getInstance(Locale.getDefault()).format(item.lprice.toLong())
        binding.lowestPriceTxt.text = getString(R.string.won, formattedPrice)
        binding.btnMove.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(checkLink(item.productId, item.mallName))
            startActivity(intent)
        }

        setSaveBtnTxt()


    }

    private fun setSaveBtnTxt() {
        binding.btnSave.text = if(isSavedItem) getString(R.string.remove) else getString(R.string.save)
    }

    private fun checkLink(productId: String, mallName: String): String {
        return if(mallName == "네이버") {
            getString(R.string.redirect_naver_link, productId)
        } else {
            getString(R.string.redirect_other_mall_link, productId)
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) {
            view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}