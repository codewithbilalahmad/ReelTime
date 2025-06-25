package com.muhammad.reeltime.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.reeltime.categories.domain.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoriesRepository: CategoryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    actionAndAdventureList = categoriesRepository.getActionAndAdventure(),
                    dramaList = categoriesRepository.getDrama(),
                    comedyList = categoriesRepository.getComedy(),
                    sciFiAndFantasyList = categoriesRepository.getSciFiAndFantasy(),
                    animationList = categoriesRepository.getAnimation()
                )
            }
        }
    }
}