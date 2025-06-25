package com.muhammad.reeltime.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.R
import com.muhammad.reeltime.auth.data.remote.dto.AuthRequest
import com.muhammad.reeltime.auth.domain.repository.AuthRepository
import com.muhammad.reeltime.auth.domain.usecase.UserDataValidator
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.UiText
import com.muhammad.reeltime.utils.asUiText
import com.muhammad.reeltime.utils.textAsFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()
    init {
        combine(
            _state.value.email.textAsFlow(),
            _state.value.password.textAsFlow(),
        ) { email, password ->
            _state.update {
                it.copy(
                    canLogin = userDataValidator.isValidEmail(
                        email = email.toString().trim()
                    ) && password.isNotEmpty()
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !state.value.isPasswordVisible) }
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggingIn = true) }
            val result = authRepository.login(
                AuthRequest(
                    email = state.value.email.text.toString().trim(),
                    password = state.value.password.text.toString().trim()
                )
            )
            _state.update { it.copy(isLoggingIn = false) }
            when (result) {
                is Result.Failure -> {
                    if(result.error == DataError.Network.UNAUTHORIZED){
                        _events.send(LoginEvent.Error(UiText.StringResource(R.string.invalid_email_or_password)))
                    } else{
                        _events.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _events.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}