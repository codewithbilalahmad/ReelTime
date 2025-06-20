package com.muhammad.reeltime.favourite.data.local

import androidx.room.*

@Database(
    entities = [FavoriteMediaEntity::class],
    version = 1
)
abstract class FavoriteMediaDatabase : RoomDatabase() {
    abstract val favoriteMediaDao: FavoriteMediaDao
}