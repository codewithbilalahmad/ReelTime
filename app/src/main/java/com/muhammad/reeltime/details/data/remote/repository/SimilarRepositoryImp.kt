package com.muhammad.reeltime.details.data.remote.repository

import com.muhammad.reeltime.details.domain.repository.SimilarRepository
import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.home.data.mapper.toMedia
import com.muhammad.reeltime.home.data.remote.dto.MediaDto
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.main.ReelTimeApplication
import com.muhammad.reeltime.utils.APIConstants.API_KEY
import com.muhammad.reeltime.utils.APIConstants.BASE_URL
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.get
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimilarRepositoryImp(
    private val httpClient: HttpClient,
    private val mainRepository: HomeRepository,
    private val favouriteRepository: FavouriteRepository,
) : SimilarRepository {
    private val context = ReelTimeApplication.INSTANCE
    override suspend fun getSimilarMedia(id: Int): Flow<Result<List<Media>, DataError.Network>> {
        return flow {
            val media = mainRepository.getMediaById(id)
            val localSimilarList = mainRepository.getMediaListByIds(media.similarMediaIds)
            if (localSimilarList.isNotEmpty()) {
                emit(Result.Success(localSimilarList))
                return@flow
            }
            val type = media.mediaType
            val response = httpClient.get<List<MediaDto>>(
                route = "$BASE_URL/$type/$id/similar",
                queryParameters = mapOf("api_key" to API_KEY, "page" to 1)
            )
            when(response){
                is Result.Failure -> {
                    emit(Result.Failure(response.error))
                    return@flow
                }
                is Result.Success -> {
                    val similarMediaDtos = response.data
                    val similarId = similarMediaDtos.map { it.id ?: 0 }
                    mainRepository.upsertMediaItem(media.copy(similarMediaIds = similarId))
                    val similarMedia = similarMediaDtos.map { mediaDto ->
                        val favouriteMedia = favouriteRepository.getMediaItemById(mediaDto.id ?: 0)
                        mediaDto.toMedia(
                            media.category,
                            isLiked = favouriteMedia?.isLiked ?: false,
                            isBookmarked = favouriteMedia?.isBookmarked ?: false
                        )
                    }
                    mainRepository.upsertMediaList(similarMedia)
                    emit(Result.Success(mainRepository.getMediaListByIds(similarId)))
                    return@flow
                }
            }
        }
    }
}