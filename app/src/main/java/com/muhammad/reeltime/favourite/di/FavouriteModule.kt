package com.muhammad.reeltime.favourite.di

import androidx.room.Room
import com.muhammad.reeltime.favourite.data.local.FavoriteMediaDatabase
import com.muhammad.reeltime.favourite.data.repository.BackendFavoritesRepositoryImpl
import com.muhammad.reeltime.favourite.data.repository.FavouriteRepositoryImp
import com.muhammad.reeltime.favourite.domain.BackendFavouriteRepository
import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.favourite.presentation.FavouriteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favouriteModule = module {
    single {
        Room.databaseBuilder(context = get(), FavoriteMediaDatabase::class.java, "favourites.db").build()
    }
    single {
        get<FavoriteMediaDatabase>().favoriteMediaDao
    }
    singleOf(::BackendFavoritesRepositoryImpl).bind<BackendFavouriteRepository>()
    singleOf(::FavouriteRepositoryImp).bind<FavouriteRepository>()
    viewModelOf(::FavouriteViewModel)
}