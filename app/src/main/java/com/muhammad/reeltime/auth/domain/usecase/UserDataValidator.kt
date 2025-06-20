package com.muhammad.reeltime.auth.domain.usecase

import android.util.Patterns

class UserDataValidator {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidUsername(name : String) : Boolean{
        return name.length in 4..50
    }
    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length > MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasUpperCaseCharacter = hasUpperCase,
            hasLowerCaseCharacter = hasLowerCase
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}