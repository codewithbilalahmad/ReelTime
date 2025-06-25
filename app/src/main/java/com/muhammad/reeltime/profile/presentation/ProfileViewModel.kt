package com.muhammad.reeltime.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.auth.domain.repository.AuthRepository
import com.muhammad.reeltime.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository : AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    username = profileRepository.getName(),
                    email = profileRepository.getEmail()
                )
            }
        }
    }
    fun onEvent(event : ProfileEvent){
        when(event){
            ProfileEvent.Logout -> {
                authRepository.logout()
            }
        }
    }
}