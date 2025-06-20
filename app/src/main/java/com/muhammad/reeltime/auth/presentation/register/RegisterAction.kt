package com.muhammad.reeltime.auth.presentation.register

sealed interface RegisterAction{
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
    data object OnTogglePasswordVisibility : RegisterAction
}