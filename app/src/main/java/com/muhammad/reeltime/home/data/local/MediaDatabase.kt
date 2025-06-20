package com.muhammad.reeltime.home.data.local

import androidx.room.*

@Database(
    entities = [MediaEntity::class],
    version = 1
)
abstract class MediaDatabase : RoomDatabase(){
    abstract val mediaDao : MediaDao
}