package com.muhammad.reeltime.search.domain

import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.Flow

interface SearchRespository {
    suspend fun getSearchList(
        query : String,
        page : Int
    ) :Flow<Result<List<Media>, DataError.Network>>
}