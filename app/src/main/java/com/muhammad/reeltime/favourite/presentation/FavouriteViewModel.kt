package com.muhammad.reeltime.favourite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val favouriteRepository: FavouriteRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()
    init {
        onEvent(FavouriteEvent.Refresh)
        viewModelScope.launch {
            favouriteRepository.favouriteDbUpdateEventFlow().collect { isUpdate ->
                if(isUpdate){
                    onEvent(FavouriteEvent.Refresh)
                }
            }
        }
    }
    fun onEvent(event : FavouriteEvent){
        when(event){
            FavouriteEvent.Refresh -> {
                loadLikedList()
                loadBookmarkedList()
            }
        }
    }
    private fun loadLikedList(){
        viewModelScope.launch {
            _state.update { it.copy(likedList = favouriteRepository.getLikedMediaList()) }
        }
    }
    private fun loadBookmarkedList(){
        viewModelScope.launch {
            _state.update { it.copy(bookmarkedList = favouriteRepository.getLikedMediaList()) }
        }
    }
}