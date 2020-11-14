package com.kimjinhwan.android.naverapi.di

import android.content.Context
import com.kimjinhwan.android.naverapi.view.DetailActivity
import com.kimjinhwan.android.naverapi.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
}