package com.muhammad.reeltime.home.di

import androidx.room.Room
import com.muhammad.reeltime.home.data.local.MediaDatabase
import com.muhammad.reeltime.home.data.repository.MainRepositoryImp
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    single {
        Room.databaseBuilder(get(), MediaDatabase::class.java, "media.db").build()
    }
    single {
        get<MediaDatabase>().mediaDao
    }
    singleOf(::MainRepositoryImp).bind<HomeRepository>()
}