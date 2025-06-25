package com.muhammad.reeltime.search.presentation

import com.muhammad.reeltime.home.domain.model.Media


sealed interface SearchEvent{
    data class OnSearchQueryChange(
        val search : String
    ) : SearchEvent
    data class OnSearchItemClick(
        val media : Media
    ) : SearchEvent
    data object OnPaginate : SearchEvent
}