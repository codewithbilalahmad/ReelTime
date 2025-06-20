package com.muhammad.reeltime.details.data.remote.repository

import com.muhammad.reeltime.details.data.remote.dto.VideosDto
import com.muhammad.reeltime.details.domain.repository.VideoRepository
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

class VideoRepositoryImp(
    private val httpClient : HttpClient,
    private val mainRepository: HomeRepository
) : VideoRepository{
    private val context = ReelTimeApplication.INSTANCE
    override suspend fun getVideos(
        id: Int,
        isRefreshing: Boolean,
    ): Flow<Result<List<String>,  DataError>> {
        return flow {
            val media = mainRepository.getMediaById(id)
            val doVideoExists = media.videosIds.isNotEmpty()
            if(doVideoExists && !isRefreshing){
                emit(Result.Success(media.videosIds))
                return@flow
            }
            val type= media.mediaType
            val response = httpClient.get<VideosDto>(route = "$BASE_URL/$type/$id/videos", queryParameters = mapOf(
                "api_key" to API_KEY
            ))
            when(response){
                is Result.Failure -> {
                    Result.Failure(response.error)
                    return@flow
                }
                is Result.Success -> {
                    val videoIds = response.data.results.map { it.key }
                    if(videoIds.isNotEmpty() == true){
                        mainRepository.upsertMediaItem(
                            media.copy(videosIds = videoIds)
                        )
                        emit(Result.Success(mainRepository.getMediaById(id).videosIds))
                    } else{
                        return@flow
                    }
                }
            }
        }
    }
}