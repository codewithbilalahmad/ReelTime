package com.muhammad.reeltime.favourite.data.repository

import android.content.SharedPreferences
import com.muhammad.reeltime.favourite.data.remote.dto.request.MediaByIdRequest
import com.muhammad.reeltime.favourite.data.remote.dto.request.MediaRequest
import com.muhammad.reeltime.favourite.data.remote.dto.response.MediaResponse
import com.muhammad.reeltime.favourite.domain.BackendFavouriteRepository
import com.muhammad.reeltime.utils.BackendConstants.BACKEND_BASE_URL
import com.muhammad.reeltime.utils.BackendConstants.GET_BOOKMARKED_MEDIA_LIST
import com.muhammad.reeltime.utils.BackendConstants.GET_LIKED_MEDIA_LIST
import com.muhammad.reeltime.utils.BackendConstants.GET_MEDIA_BY_ID
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.get
import com.muhammad.reeltime.utils.post
import io.ktor.client.HttpClient

class BackendFavoritesRepositoryImpl(
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences,
) : BackendFavouriteRepository {
    override suspend fun getLikedMediaList(): Result<List<MediaResponse>, DataError.Network> {
        val email =
            prefs.getString("email", null) ?: return Result.Failure(DataError.Network.UNKNOWN)
        return httpClient.get<List<MediaResponse>>(
            route = "${BACKEND_BASE_URL}${GET_LIKED_MEDIA_LIST}",
            queryParameters = mapOf("email" to email)
        )
    }

    override suspend fun getBookmarkMediaList(): Result<List<MediaResponse>, DataError.Network> {
        val email =
            prefs.getString("email", null) ?: return Result.Failure(DataError.Network.UNKNOWN)
        return httpClient.get<List<MediaResponse>>(
            route = "${BACKEND_BASE_URL}${GET_BOOKMARKED_MEDIA_LIST}",
            queryParameters = mapOf("email" to email)
        )
    }

    override suspend fun getMediaById(mediaId: Int): Result<MediaResponse, DataError.Network> {
        val email =
            prefs.getString("email", null) ?: return Result.Failure(DataError.Network.UNKNOWN)
        return httpClient.get<MediaResponse>(
            route = "${BACKEND_BASE_URL}${GET_MEDIA_BY_ID}",
            queryParameters = mapOf("email" to email, "mediaId" to mediaId)
        )
    }

    override suspend fun upsertMediaToUser(mediaRequest: MediaRequest): Result<Boolean, DataError.Network> {
        return httpClient.post<MediaRequest, Boolean>(
            route = "${BACKEND_BASE_URL}${GET_MEDIA_BY_ID}", body = mediaRequest
        )
    }

    override suspend fun deleteMediaFromUser(mediaId: Int): Result<Boolean, DataError.Network> {
        val email = prefs.getString("email", null) ?: return Result.Failure(DataError.Network.UNKNOWN)
        return httpClient.post<MediaByIdRequest, Boolean>(
            route = "${BACKEND_BASE_URL}${GET_MEDIA_BY_ID}",
            body = MediaByIdRequest(mediaId = mediaId, email = email)
        )
    }
}