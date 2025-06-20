package com.muhammad.reeltime.auth.presentation.login

import com.muhammad.reeltime.utils.UiText

sealed interface LoginEvent{
    data class Error(val text : UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}