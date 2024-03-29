package com.kimjinhwan.android.naverapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.kimjinhwan.android.naverapi.R
import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        Glide.with(this).load(R.drawable.naver_openapi_logo).into(logoImage)
    }
}