package com.muhammad.reeltime.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.profile.domain.ProfileRepository
import com.muhammad.reeltime.utils.APIConstants
import com.muhammad.reeltime.utils.APIConstants.ALL
import com.muhammad.reeltime.utils.APIConstants.MOVIE
import com.muhammad.reeltime.utils.APIConstants.POPULAR
import com.muhammad.reeltime.utils.APIConstants.TRENDING_TIME
import com.muhammad.reeltime.utils.APIConstants.TV
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            _state.update { it.copy(name = profileRepository.getName()) }
            onEvent(HomeEvent.LoadAllData)
        }
    }
    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadAllData ->{
                loadTrending()
                loadTv()
                loadMovies()
            }
            is HomeEvent.Paginate -> {
                when(event.destination){
                    Destinations.TrendingScreen ->{
                        loadTrending(forceFetchFromRemote = true)
                    }
                    Destinations.TvScreen -> {
                        loadTv(forceFetchFromRemote = true)
                    }
                    Destinations.MovieScreen ->{
                        loadMovies(forceFetchFromRemote = true)
                    }
                    else -> Unit
                }
            }
            is HomeEvent.Refresh ->{
                when(event.destination){
                    Destinations.HomeScreen ->{
                        _state.update { it.copy(specialList = emptyList()) }
                        loadTrending(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                        loadTv(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                        loadMovies(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                    }
                    Destinations.TrendingScreen ->{
                        loadTrending(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                    }
                    Destinations.TvScreen ->{
                        loadTv(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                    }
                    Destinations.MovieScreen ->{
                        loadMovies(
                            forceFetchFromRemote = true, isRefresh = true
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun loadSpecial(list: List<Media>) {
        if (state.value.specialList.size <= 4) {
            _state.update {
                it.copy(
                    specialList = it.specialList + list.take(2)
                )
            }
        }
    }
    private fun loadTrending(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            homeRepository.getTreading(
                forceFetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                type = ALL,
                time = TRENDING_TIME,
                page = state.value.trendingPage
            ).collect { result ->
                when (result) {
                    is Result.Failure -> {
                        _state.value = state.value.copy(isLoading = false)
                    }
                    is Result.Success -> {
                        result.data.let { mediaList ->
                            _state.value = state.value.copy(isLoading = false)
                            val shuffledList = mediaList.shuffled()
                            if (isRefresh) {
                                _state.update {
                                    it.copy(trendingList = shuffledList, trendingPage = 2)
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _state.update {
                                    it.copy(
                                        trendingList = it.trendingList + shuffledList,
                                        trendingPage = it.trendingPage + 1
                                    )
                                }
                                if(!forceFetchFromRemote){
                                    loadSpecial(shuffledList)
                                }
                            }
                        }
                        _state.value = state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }
    private fun loadTv(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            homeRepository.getMoviesAndTv(
                forceFetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                type = TV,
                time = POPULAR,
                page = state.value.tvPage
            ).collect { result ->
                when (result) {
                    is Result.Failure -> {
                        _state.value = state.value.copy(isLoading = false)
                    }
                    is Result.Success -> {
                        result.data.let { mediaList ->
                            _state.value = state.value.copy(isLoading = false)
                            val shuffledList = mediaList.shuffled()
                            if (isRefresh) {
                                _state.update {
                                    it.copy(tvList = shuffledList, tvPage = 2)
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _state.update {
                                    it.copy(
                                        tvList = it.tvList + shuffledList,
                                        tvPage = it.tvPage + 1
                                    )
                                }
                                if(!forceFetchFromRemote){
                                    loadSpecial(shuffledList)
                                }
                            }
                        }
                        _state.value = state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }
    private fun loadMovies(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false,
    ) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            homeRepository.getMoviesAndTv(
                forceFetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                type = MOVIE,
                time = POPULAR,
                page = state.value.moviesPage
            ).collect { result ->
                when (result) {
                    is Result.Failure -> {
                        _state.value = state.value.copy(isLoading = false)
                    }

                    is Result.Success -> {
                        result.data.let { mediaList ->
                            _state.value = state.value.copy(isLoading = false)
                            val shuffledList = mediaList.shuffled()
                            if (isRefresh) {
                                _state.update {
                                    it.copy(moviesList = shuffledList, moviesPage = 2)
                                }
                                loadSpecial(shuffledList)
                            } else {
                                _state.update {
                                    it.copy(
                                        moviesList = it.moviesList + shuffledList,
                                        moviesPage = it.moviesPage + 1
                                    )
                                }
                                if(!forceFetchFromRemote){
                                    loadSpecial(shuffledList)
                                }
                            }
                        }
                        _state.value = state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }
}