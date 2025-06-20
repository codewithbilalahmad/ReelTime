package com.muhammad.reeltime.auth.domain.usecase

data class PasswordValidationState(
    val hasMinLength : Boolean = false,
    val hasNumber : Boolean = false,
    val hasLowerCaseCharacter : Boolean = false,
    val hasUpperCaseCharacter : Boolean = false,
){
    val isValidPassword = hasMinLength && hasNumber && hasLowerCaseCharacter && hasUpperCaseCharacter
}
