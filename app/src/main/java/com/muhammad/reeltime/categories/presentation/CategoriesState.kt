package com.muhammad.reeltime.categories.presentation

import com.muhammad.reeltime.home.domain.model.Media

data class CategoriesState(
    val actionAndAdventureList: List<Media> = emptyList(),
    val dramaList : List<Media> = emptyList(),
    val comedyList : List<Media> = emptyList(),
    val sciFiAndFantasyList : List<Media> = emptyList(),
    val animationList : List<Media> = emptyList(),
)
