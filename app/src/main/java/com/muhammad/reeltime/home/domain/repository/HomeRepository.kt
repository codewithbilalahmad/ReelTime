package com.muhammad.reeltime.home.domain.repository

import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun upsertMediaList(mediaList : List<Media>)
    suspend fun upsertMediaItem(media : Media)
    suspend fun getMediaListByCategory(category : String) : List<Media>
    suspend fun getMediaById(id : Int) : Media
    suspend fun getMediaListByIds(ids : List<Int>) : List<Media>
    suspend fun getTreading(
        forceFetchFromRemote : Boolean,
        isRefresh : Boolean,
        type : String,
        time : String,
        page : Int
    ) : Flow<Result<List<Media>, DataError.Network>>
    suspend fun getMoviesAndTv(
        forceFetchFromRemote : Boolean,
        isRefresh : Boolean,
        type : String,
        time : String,
        page : Int
    ) : Flow<Result<List<Media>, DataError.Network>>
    suspend fun clearDatabase()
}