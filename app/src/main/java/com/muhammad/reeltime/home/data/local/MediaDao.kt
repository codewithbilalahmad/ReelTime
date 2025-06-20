package com.muhammad.reeltime.home.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MediaDao {
    @Upsert
    suspend fun upsertMediaList(mediaList: List<MediaEntity>)
    @Query("SELECT * FROM MediaEntity WHERE mediaId IN (:ids)")
    suspend fun getMediaListByIds(
        ids: List<Int>
    ): List<MediaEntity>
    @Upsert
    suspend fun upsertMediaItem(media: MediaEntity)
    @Query("SELECT * FROM MediaEntity WHERE category = :category")
    suspend fun getMediaListByCategory(
        category: String
    ): List<MediaEntity>
    @Query("SELECT * FROM mediaentity")
    suspend fun getMediaList(): List<MediaEntity>

    @Query("SELECT * FROM mediaentity WHERE mediaType =:mediaType AND category =:category")
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String,
        category: String,
    ): List<MediaEntity>
    @Query("SELECT * FROM mediaentity WHERE mediaId = :mediaId")
    suspend fun getMediaById(
        mediaId : Int
    ) : MediaEntity
    @Query("SELECT COUNT(*) FROM mediaentity WHERE mediaId =:mediaId")
    suspend fun doesMediaExists(mediaId : Int) : Int
    @Query("DELETE FROM MediaEntity")
    suspend fun deleteAllMediaItem()

    @Query("DELETE FROM MediaEntity WHERE mediaType = :mediaType AND category = :category")
    suspend fun deleteAllMediaListByTypeAndCategory(
        mediaType: String, category: String
    )

    @Query("DELETE FROM MediaEntity WHERE category = :category")
    suspend fun deleteAllMediaListByCategory(
        category: String
    )
}