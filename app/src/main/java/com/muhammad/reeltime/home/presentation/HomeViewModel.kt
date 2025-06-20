package com.muhammad.reeltime.home.presentation

import androidx.lifecycle.ViewModel
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val profileRepository: ProfileRepository
) : ViewModel(){
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    fun onEvent(event : HomeEvent){
        when(event){
            HomeEvent.LoadAllData -> TODO()
            is HomeEvent.Paginate -> TODO()
            is HomeEvent.Refresh -> TODO()
        }
    }
    private fun loadMovies(
        forceFetchFromRemote : Boolean = false,
        isRefresh : Boolean = false
    ){

    }
}