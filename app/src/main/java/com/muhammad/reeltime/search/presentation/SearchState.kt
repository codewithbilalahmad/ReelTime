package com.muhammad.reeltime.search.presentation

import com.muhammad.reeltime.home.domain.model.Media

data class SearchState(
    val isLoading : Boolean = false,
    val searchPage : Int = 1,
    val searchQuery : String = "",
    val searchList : List<Media> = emptyList()
)