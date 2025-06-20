package com.muhammad.reeltime.details.data.remote.repository

import com.muhammad.reeltime.details.data.remote.dto.DetailsDto
import com.muhammad.reeltime.details.domain.repository.DetailRepository
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.utils.APIConstants.BASE_URL
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.asUiText
import com.muhammad.reeltime.utils.get
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepositoryImp(
    private val  httpClient : HttpClient,
    private val mainRepository : HomeRepository
) : DetailRepository{
    override suspend fun getDetails(
        id: Int,
        isRefreshing: Boolean,
    ): Flow<Result<Media, DataError.Network>> {
        return flow {
            val media = mainRepository.getMediaById(id)
            val doDetailsExists = media.runTime != 0 || media.tagLine.isNotEmpty()
            if(doDetailsExists && !isRefreshing){
                emit(Result.Success(media))
                return@flow
            }
            val type = media.mediaType
            val response =  httpClient.get<DetailsDto>(route = "$BASE_URL/$type/$id", queryParameters = mapOf())
            when(response){
                is Result.Failure ->{
                    emit(Result.Failure(response.error))
                    return@flow
                }
                is Result.Success -> {
                    val detail = response.data
                    val mediaWithDetails = media.copy(
                        runTime = detail.runtime ?: 0,
                        tagLine = detail.tagline ?: ""
                    )
                    mainRepository.upsertMediaItem(mediaWithDetails)
                    emit(Result.Success(mainRepository.getMediaById(id)))
                    return@flow
                }
            }
        }
    }
}