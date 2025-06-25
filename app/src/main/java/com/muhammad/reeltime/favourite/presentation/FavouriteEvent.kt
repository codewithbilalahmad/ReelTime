package com.muhammad.reeltime.favourite.presentation

sealed interface FavouriteEvent{
    data object Refresh : FavouriteEvent
}