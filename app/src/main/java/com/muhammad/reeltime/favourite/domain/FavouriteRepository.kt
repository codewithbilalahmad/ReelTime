package com.muhammad.reeltime.favourite.domain

import com.muhammad.reeltime.home.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun favouriteDbUpdateEventFlow() : Flow<Boolean>
    suspend fun upsetFavoritesMediaItem(
        media: Media
    )

    suspend fun deleteFavoritesMediaItem(
        media: Media
    )

    suspend fun getMediaItemById(
        mediaId: Int
    ): Media?

    suspend fun getLikedMediaList(): List<Media>
    suspend fun getBookmarkedMediaList(): List<Media>

    suspend fun clearMainDb()
}