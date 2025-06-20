package com.muhammad.reeltime.auth.presentation.register

import com.muhammad.reeltime.utils.UiText

sealed interface RegisterEvent{
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error : UiText) : RegisterEvent
}