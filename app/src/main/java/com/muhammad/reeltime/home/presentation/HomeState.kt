package com.muhammad.reeltime.home.presentation

import com.muhammad.reeltime.home.domain.model.Media

data class HomeState(
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val trendingPage : Int = 1,
    val tvPage : Int = 1,
    val moviesPage : Int = 1,
    val trendingList : List<Media> = emptyList(),
    val tvList : List<Media> = emptyList(),
    val moviesList : List<Media> = emptyList(),
    val specialList : List<Media> = emptyList(),
    val name : String = ""
)