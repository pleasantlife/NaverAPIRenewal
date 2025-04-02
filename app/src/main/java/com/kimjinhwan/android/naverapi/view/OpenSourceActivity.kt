package com.kimjinhwan.android.naverapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.databinding.ActivityOpenSourceBinding

class OpenSourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupWindowInsets()
        super.onCreate(savedInstanceState)
        binding = ActivityOpenSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        Glide.with(this).load(R.drawable.naver_openapi_logo).into(findViewById(R.id.logoImage))
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) {
                view, insets ->
            val statusBar = WindowInsetsCompat.Type.statusBars()
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}