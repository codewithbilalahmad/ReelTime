package com.muhammad.reeltime.profile.presentation

sealed interface ProfileEvent {
    data object Logout : ProfileEvent
}