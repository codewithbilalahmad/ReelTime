package com.muhammad.reeltime.details.presentation.similarList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.details.presentation.detail.DetailViewModel
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.MediaImageAndTitle
import com.muhammad.reeltime.ui.components.NonFocusedTopBar

@Composable
fun SimilarScreen(
    navHostController: NavHostController,
    viewModel: DetailViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GradientBackground(hasTopbar = true) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            NonFocusedTopBar(
                title = "Similar to ${state.media?.title}",
                navHostController = navHostController
            )
        }) { paddingValues ->
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
                columns = GridCells.Fixed(2)
            ) {
                items(state.similarList) { media ->
                    MediaImageAndTitle(media = media, navHostController = navHostController)
                }
            }
        }
    }
}