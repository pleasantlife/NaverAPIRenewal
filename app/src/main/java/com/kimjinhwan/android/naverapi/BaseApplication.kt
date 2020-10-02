package com.kimjinhwan.android.naverapi

import android.app.Application
import com.kimjinhwan.android.naverapi.di.AppComponent
import com.kimjinhwan.android.naverapi.di.DaggerAppComponent

open class BaseApplication: Application() {

    val appComponent : AppComponent by lazy {
       DaggerAppComponent.factory().create(applicationContext)
    }
}