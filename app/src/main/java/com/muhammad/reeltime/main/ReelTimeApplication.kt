package com.muhammad.reeltime.main

import android.app.Application
import com.muhammad.reeltime.auth.di.authModule
import com.muhammad.reeltime.categories.di.categoryModule
import com.muhammad.reeltime.favourite.di.favouriteModule
import com.muhammad.reeltime.home.di.homeModule
import com.muhammad.reeltime.main.di.mainModule
import com.muhammad.reeltime.profile.di.profileModule
import com.muhammad.reeltime.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ReelTimeApplication : Application() {
    companion object {
        lateinit var INSTANCE: ReelTimeApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@ReelTimeApplication)
            androidLogger()
            modules(
                mainModule, homeModule, authModule,
                categoryModule, favouriteModule, searchModule, profileModule
            )
        }
    }
}