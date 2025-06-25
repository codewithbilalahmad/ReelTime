package com.muhammad.reeltime.details.presentation.detail

import com.muhammad.reeltime.home.domain.model.Media

data class DetailState(
    val media : Media?=null,
    val videoId : String = "",
    val readableTime : String = "",
    val videoList : List<String> =emptyList(),
    val similarList : List<Media> = emptyList(),
    val showDialog : Boolean = false,
    val isRemoveFromFavouriteDialog : Boolean = false,
)