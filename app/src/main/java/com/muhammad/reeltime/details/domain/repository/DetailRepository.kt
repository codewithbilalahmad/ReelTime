package com.muhammad.reeltime.details.domain.repository

import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetails(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Result<Media, DataError.Network>>
}