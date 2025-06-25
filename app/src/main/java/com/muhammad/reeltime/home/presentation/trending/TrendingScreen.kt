package com.muhammad.reeltime.home.presentation.trending

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.presentation.HomeViewModel
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.MediaImageAndTitle
import com.muhammad.reeltime.ui.components.NonFocusedTopBar

@Composable
fun TrendingScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GradientBackground(hasTopbar = true) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            NonFocusedTopBar(
                title = stringResource(R.string.trending),
                navHostController = navHostController
            )
        }) { paddingValues ->
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
                columns = GridCells.Fixed(2)
            ) {
                items(state.trendingList) { media ->
                    MediaImageAndTitle(media = media, navHostController = navHostController)
                }
            }
        }
    }
}