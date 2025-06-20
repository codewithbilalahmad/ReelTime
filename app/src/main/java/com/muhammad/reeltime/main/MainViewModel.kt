package com.muhammad.reeltime.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences:  SharedPreferences
): ViewModel(){
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            _state.update { it.copy(isCheckingAuth = true) }
            _state.update { it.copy(isLoggedIn = preferences.getBoolean("isLoggedIn", false) ?: false) }
            _state.update { it.copy(isCheckingAuth = false) }
        }
    }
}