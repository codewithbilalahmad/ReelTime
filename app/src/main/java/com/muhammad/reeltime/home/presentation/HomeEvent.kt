package com.muhammad.reeltime.home.presentation

import com.muhammad.reeltime.main.navigation.Destinations

sealed interface HomeEvent{
    data class Refresh(val destination : Destinations) : HomeEvent
    data class Paginate(val destination : Destinations) : HomeEvent
    data object LoadAllData : HomeEvent
}