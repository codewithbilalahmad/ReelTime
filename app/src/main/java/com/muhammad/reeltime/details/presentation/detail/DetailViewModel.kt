package com.muhammad.reeltime.details.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.details.domain.repository.DetailRepository
import com.muhammad.reeltime.details.domain.repository.SimilarRepository
import com.muhammad.reeltime.details.domain.repository.VideoRepository
import com.muhammad.reeltime.details.domain.usecase.MinutesToReadableTime
import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val homeRepository: HomeRepository,
    private val detailRepository: DetailRepository,
    private val videoRepository: VideoRepository,
    private val similarRespository: SimilarRepository,
    private val favouriteRepository: FavouriteRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()
    private val _navigateToWatchVideoChannel = Channel<Boolean>()
    val navigateToWatchVideoChannel = _navigateToWatchVideoChannel.receiveAsFlow()
    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.NavigateToWatchVideo -> {
                viewModelScope.launch {
                    if (state.value.videoId.isNotEmpty()) {
                        _navigateToWatchVideoChannel.send(true)
                    } else {
                        _navigateToWatchVideoChannel.send(false)
                    }
                }
            }

            DetailEvent.Refresh -> loadMediaItem(isRefresh = true)
            is DetailEvent.StartLoading -> {
                loadMediaItem(id = event.mediaId)
            }

            DetailEvent.ToggleBookmark -> toggleBookmark()
            is DetailEvent.ToggleDialog -> {
                val media = state.value.media
                if (event.isRemoveFromFavouriteDialog && media?.isLiked == false) {
                    toggleLike()
                    return
                }
                if (!event.isRemoveFromFavouriteDialog && media?.isBookmarked == false) {
                    toggleBookmark()
                    return
                }
                _state.update {
                    it.copy(
                        showDialog = !state.value.showDialog,
                        isRemoveFromFavouriteDialog = event.isRemoveFromFavouriteDialog
                    )
                }
            }

            DetailEvent.ToggleLike -> toggleLike()
        }
    }

    private fun loadSimilar() {
        viewModelScope.launch {
            similarRespository.getSimilarMedia(id = state.value.media?.mediaId ?: 0)
                .collect { result ->
                    when (result) {
                        is Result.Failure -> Unit
                        is Result.Success -> {
                            val data = result.data
                            data.let { similarList ->
                                _state.update {
                                    it.copy(similarList = similarList)
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun loadMediaItem(
        isRefresh: Boolean = false, id: Int = state.value.media?.mediaId ?: 0,
    ) {
        viewModelScope.launch {
            _state.update { it.copy(media = homeRepository.getMediaById(id)) }
            loadDetails(isRefresh)
            loadVideos(isRefresh)
            loadSimilar()
        }
    }

    private fun loadDetails(isRefresh: Boolean) {
        viewModelScope.launch {
            detailRepository.getDetails(
                id = state.value.media?.mediaId ?: 0,
                isRefreshing = isRefresh
            )
                .collect { result ->
                    when (result) {
                        is Result.Failure -> Unit
                        is Result.Success -> {
                            val data = result.data
                            data.let { media ->
                                _state.update {
                                    it.copy(
                                        media = it.media?.copy(
                                            runTime = media.runTime,
                                            tagLine = media.tagLine
                                        ), readableTime = if (media.runTime != 0) {
                                            MinutesToReadableTime(media.runTime).invoke()
                                        } else ""
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun loadVideos(isRefresh: Boolean) {
        viewModelScope.launch {
            videoRepository.getVideos(
                id = state.value.media?.mediaId ?: 0,
                isRefreshing = isRefresh
            )
                .collect { result ->
                    when (result) {
                        is Result.Failure -> Unit
                        is Result.Success -> {
                            val data = result.data
                            data.let { videos ->
                                _state.update {
                                    it.copy(videoList = videos, videoId = videos.shuffled()[0])
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun toggleLike() {
        _state.update {
            it.copy(
                media = it.media?.copy(isLiked = !it.media.isLiked),
                isRemoveFromFavouriteDialog = true,
                showDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun toggleBookmark() {
        _state.update {
            it.copy(
                media = it.media?.copy(isBookmarked = !it.media.isBookmarked),
                isRemoveFromFavouriteDialog = false,
                showDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun updateOrDeleteMedia() {
        viewModelScope.launch {
            state.value.media?.let { media ->
                if (!media.isLiked && !media.isBookmarked) {
                    favouriteRepository.deleteFavoritesMediaItem(media)
                } else {
                    homeRepository.upsertMediaItem(media)
                    favouriteRepository.upsetFavoritesMediaItem(media)
                }
            }
        }
    }
}