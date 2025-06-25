package com.muhammad.reeltime.details.presentation.detail

sealed interface DetailEvent{
    data class StartLoading(val mediaId : Int) : DetailEvent
    data object Refresh : DetailEvent
    data object NavigateToWatchVideo : DetailEvent
    data class ToggleDialog(val isRemoveFromFavouriteDialog : Boolean): DetailEvent
    data object ToggleLike : DetailEvent
    data object ToggleBookmark : DetailEvent
}