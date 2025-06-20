package com.muhammad.reeltime.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.muhammad.reeltime.auth.domain.usecase.PasswordValidationState

data class RegisterState(
    val username : TextFieldState = TextFieldState(),
    val email : TextFieldState = TextFieldState(),
    val password : TextFieldState = TextFieldState(),
    val isEmailValid : Boolean = false,
    val isPasswordVisible : Boolean = false,
    val passwordValidationState : PasswordValidationState = PasswordValidationState(),
    val isRegistering : Boolean = false,
    val canRegister : Boolean = false
)