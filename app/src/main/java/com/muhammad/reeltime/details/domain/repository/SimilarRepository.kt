package com.muhammad.reeltime.details.domain.repository

import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.Flow

interface SimilarRepository{
    suspend fun getSimilarMedia(id : Int) : Flow<Result<List<Media>, DataError.Network>>
}