package com.muhammad.reeltime.categories.presentation.category_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.categories.presentation.CategoriesState
import com.muhammad.reeltime.categories.presentation.CategoriesViewModel
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.MediaImageAndTitle
import com.muhammad.reeltime.ui.components.NonFocusedTopBar
import com.muhammad.reeltime.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesListScreen(
    category: String,
    navHostController: NavHostController,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoriesListScreenContent(
        category = category,
        navHostController = navHostController,
        state = state
    )
}

@Composable
fun CategoriesListScreenContent(
    category: String,
    navHostController: NavHostController,
    state: CategoriesState,
) {

    val mediaList = when (category) {
        Constants.actionAndAdventureList -> state.actionAndAdventureList
        Constants.dramaList -> state.dramaList
        Constants.comedyList -> state.comedyList
        Constants.sciFiAndFantasyList -> state.sciFiAndFantasyList
        else -> state.animationList
    }
    GradientBackground(hasTopbar = true){
        Scaffold(Modifier.fillMaxSize(), topBar = {
            NonFocusedTopBar(
                navHostController = navHostController,
                title = category
            )
        }) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(mediaList, key = {it.mediaId}) { media ->
                    MediaImageAndTitle(media = media, navHostController = navHostController)
                }
            }
        }
    }
}