package com.muhammad.reeltime.details.domain.repository

import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(
        id : Int,
        isRefreshing : Boolean
    ) : Flow<Result<List<String>,  DataError>>
}