package com.muhammad.reeltime.favourite.domain

import com.muhammad.reeltime.favourite.data.remote.dto.request.MediaRequest
import com.muhammad.reeltime.favourite.data.remote.dto.response.MediaResponse
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result

interface BackendFavouriteRepository {
    suspend fun getLikedMediaList(): Result<List<MediaResponse>, DataError.Network>
    suspend fun getBookmarkMediaList(): Result<List<MediaResponse>, DataError.Network>
    suspend fun getMediaById(mediaId: Int): Result<MediaResponse, DataError.Network>
    suspend fun upsertMediaToUser(mediaRequest: MediaRequest): Result<Boolean, DataError.Network>
    suspend fun deleteMediaFromUser(mediaId: Int): Result<Boolean, DataError.Network>
}