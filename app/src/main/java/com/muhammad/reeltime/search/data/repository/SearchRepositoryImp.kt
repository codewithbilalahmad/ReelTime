package com.muhammad.reeltime.search.data.repository

import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.home.data.mapper.toMedia
import com.muhammad.reeltime.home.data.remote.dto.MediaListDto
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.search.domain.SearchRespository
import com.muhammad.reeltime.utils.APIConstants.API_KEY
import com.muhammad.reeltime.utils.APIConstants.BASE_URL
import com.muhammad.reeltime.utils.APIConstants.POPULAR
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.get
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImp(
    private val httpClient: HttpClient,
    private val favouriteRepository: FavouriteRepository,
) : SearchRespository {
    override suspend fun getSearchList(
        query: String,
        page: Int,
    ): Flow<Result<List<Media>, DataError.Network>> {
        return flow {
            val response = httpClient.get<MediaListDto>(
                route = "${BASE_URL}search/multi", queryParameters = mapOf(
                    "page" to page, "query" to query, "api_key" to API_KEY
                )
            )
            when (response) {
                is Result.Failure -> {
                    Result.Failure(response.error)
                    return@flow
                }

                is Result.Success -> {
                    val mediaDtos = response.data.results
                    if (mediaDtos?.isNotEmpty() == true) {
                        val mediaList = mediaDtos.map { mediaDto ->
                            val favouriteMedia = favouriteRepository.getMediaItemById(mediaDto.id ?: 0)
                            mediaDto.toMedia(
                                POPULAR,
                                isLiked = favouriteMedia?.isLiked ?: false,
                                isBookmarked = favouriteMedia?.isBookmarked ?: false
                            )
                        }
                        emit(Result.Success(mediaList))
                        return@flow
                    } else {
                        return@flow
                    }
                }
            }
        }
    }
}