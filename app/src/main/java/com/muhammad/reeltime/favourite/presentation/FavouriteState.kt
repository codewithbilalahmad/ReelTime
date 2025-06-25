package com.muhammad.reeltime.favourite.presentation

import com.muhammad.reeltime.home.domain.model.Media

data class FavouriteState(
    val likedList : List<Media> = emptyList(),
    val bookmarkedList : List<Media> = emptyList(),
)