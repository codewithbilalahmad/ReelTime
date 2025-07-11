package com.muhammad.reeltime.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.home.domain.repository.HomeRepository
import com.muhammad.reeltime.search.domain.SearchRespository
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val homeRepository: HomeRepository,
    private val searchRepository: SearchRespository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()
    private val _navigateToDetailChannel = Channel<Int>()
    val navigateToDetailChannel = _navigateToDetailChannel.receiveAsFlow()
    private var searchJob: Job? = null

    init {
        observeSearchResults()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.OnPaginate -> {
                if(state.value.searchQuery.isNotEmpty()){
                    _state.update { it.copy(searchPage = it.searchPage + 1) }
                    searchJob?.cancel()
                    searchJob = searchMovies(query = state.value.searchQuery, isPaginating = true)
                }
            }

            is SearchEvent.OnSearchItemClick -> {
                viewModelScope.launch {
                    val media = event.media
                    homeRepository.upsertMediaItem(media = media)
                    _navigateToDetailChannel.send(media.mediaId)
                }
            }

            is SearchEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.search, searchList = emptyList(), searchPage = 1
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchResults() {
        state.map { it.searchQuery }.distinctUntilChanged().debounce(500L)
            .onEach { query ->
                if(query.isNotEmpty()){
                    searchJob?.cancel()
                    searchJob = searchMovies(query = query)
                }
            }.launchIn(viewModelScope)
    }

    private fun searchMovies(query: String, isPaginating: Boolean = false) = viewModelScope.launch {
        _state.update {
            if (isPaginating) it.copy(isMoreSearchLoading = true) else it.copy(
                isLoading = true
            )
        }
        searchRepository.getSearchList(
            query = query,
            page = state.value.searchPage
        ).collect { result ->
            when (result) {
                is Result.Failure -> {
                    _state.update {
                        if (isPaginating) it.copy(isMoreSearchLoading = false) else it.copy(
                            isLoading = false
                        )
                    }
                }

                is Result.Success -> {
                    println("Search Results : ${result.data}")
                    _state.update {
                        it.copy(
                            isLoading = false,isMoreSearchLoading = false,
                            searchList = it.searchList + result.data
                        )
                    }
                }
            }
        }
    }
}
