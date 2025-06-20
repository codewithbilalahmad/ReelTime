package com.muhammad.reeltime.home.data.repository

import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.home.data.local.MediaDao
import com.muhammad.reeltime.home.data.mapper.toMedia
import com.muhammad.reeltime.home.data.mapper.toMediaEntity
import com.muhammad.reeltime.home.data.remote.dto.MediaListDto
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.utils.APIConstants.API_KEY
import com.muhammad.reeltime.utils.APIConstants.BASE_URL
import com.muhammad.reeltime.utils.APIConstants.MOVIE
import com.muhammad.reeltime.utils.APIConstants.POPULAR
import com.muhammad.reeltime.utils.APIConstants.TRENDING
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.get
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImp(
    private val httpClient : HttpClient,
    private val favouriteRepository: FavouriteRepository,
    private val mediaDao: MediaDao,
) : HomeRepository {
    override suspend fun upsertMediaList(mediaList: List<Media>) {
        mediaDao.upsertMediaList(mediaList.map { it.toMediaEntity() })
    }

    override suspend fun upsertMediaItem(media: Media) {
        mediaDao.upsertMediaItem(media.toMediaEntity())
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> {
        return mediaDao.getMediaListByCategory(category).map { it.toMedia() }
    }

    override suspend fun getMediaById(id: Int): Media {
        return mediaDao.getMediaById(mediaId = id).toMedia()
    }

    override suspend fun getMediaListByIds(ids: List<Int>): List<Media> {
        return mediaDao.getMediaListByIds(ids).map { it.toMedia() }
    }

    override suspend fun getTreading(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
    ): Flow<Result<List<Media>, DataError.Network>> {
        return flow {
            val localMediaList = mediaDao.getMediaListByCategory(TRENDING)
            val loadJustFromCache =
                localMediaList.isNotEmpty() && !forceFetchFromRemote && !isRefresh
            if (loadJustFromCache) {
                emit(Result.Success(localMediaList.map { it.toMedia() }))
                return@flow
            }
            val response = httpClient.get<MediaListDto>(
                route = "${BASE_URL}treading/$type/$time", queryParameters = mapOf(
                    "api_key" to API_KEY,
                    "page" to 1
                )
            )
            when(response){
                is Result.Failure -> {
                    Result.Failure(response.error)
                    return@flow
                }
                is Result.Success -> {
                    val mediaList = response.data.results
                    if(mediaList?.isNotEmpty() == true){
                        val entities = mediaList.map { mediaDto ->
                            val favouriteMedia = favouriteRepository.getMediaItemById(mediaDto.id ?: 0)
                            mediaDto.toMediaEntity(
                                type = mediaDto.media_type ?: MOVIE,
                                category = TRENDING,
                                isLiked = favouriteMedia?.isLiked ?: false,
                                isBookmarked = favouriteMedia?.isBookmarked ?: false
                            )
                        }
                        if(isRefresh){
                            mediaDao.deleteAllMediaListByCategory(TRENDING)
                        }
                        mediaDao.upsertMediaList(entities)
                        emit(Result.Success(entities.map { it.toMedia() }))
                        return@flow
                    } else{
                        return@flow
                    }
                }
            }
        }
    }

    override suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
    ): Flow<Result<List<Media>, DataError.Network>> {
        return flow {
            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, POPULAR)
            val loadJustFromCache =
                localMediaList.isNotEmpty() && !forceFetchFromRemote && !isRefresh
            if (loadJustFromCache) {
                emit(Result.Success(localMediaList.map { it.toMedia() }))
                return@flow
            }
            val response = httpClient.get<MediaListDto>(
                route = "${BASE_URL}$type/$POPULAR", queryParameters = mapOf(
                    "api_key" to API_KEY,
                    "page" to 1
                )
            )
            when(response){
                is Result.Failure -> {
                    Result.Failure(response.error)
                    return@flow
                }
                is Result.Success -> {
                    val mediaList = response.data.results
                    if(mediaList?.isNotEmpty() == true){
                        val entities = mediaList.map { media ->
                            media.toMediaEntity(type = type, POPULAR, false, false)
                        }
                        if (isRefresh) {
                            mediaDao.deleteAllMediaListByTypeAndCategory(type, POPULAR)
                        }
                        mediaDao.upsertMediaList(entities)
                        emit(Result.Success(entities.map { it.toMedia() }))
                        return@flow
                    } else {
                        return@flow
                    }
                }
            }
        }
    }

    override suspend fun clearDatabase() {
        mediaDao.deleteAllMediaItem()
    }
}