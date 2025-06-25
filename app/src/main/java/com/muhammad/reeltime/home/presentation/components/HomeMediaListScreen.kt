package com.muhammad.reeltime.home.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.presentation.HomeEvent
import com.muhammad.reeltime.home.presentation.HomeState
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.MediaImageAndTitle
import com.muhammad.reeltime.ui.components.NonFocusedTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMediaListScreen(
    destination: Destinations,
    navHostController: NavHostController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    val mediaList = when (destination) {
        Destinations.TvScreen -> state.tvList
        Destinations.MovieScreen -> state.moviesList
        Destinations.TrendingScreen -> state.trendingList
        else -> emptyList()
    }
    val title = when (destination) {
        Destinations.TvScreen -> stringResource(R.string.tv_series)
        Destinations.MovieScreen -> stringResource(R.string.movies)
        Destinations.TrendingScreen -> stringResource(R.string.trending)
        else -> ""
    }
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun onRefresh() = scope.launch {
        refreshing = true
        delay(1500)
        onEvent(HomeEvent.Refresh(destination))
        refreshing = false
    }

    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(isRefreshing = refreshing, state = refreshState, content = {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            NonFocusedTopBar(
                navHostController = navHostController,
                title = title
            )
        }){paddingValues ->
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(190.dp),
                state = listState,
                contentPadding = paddingValues
            ) {
                items(mediaList.size) { index ->
                    MediaImageAndTitle(media = mediaList[index], navHostController = navHostController)
                    if (index >= mediaList.size - 1 && !state.isLoading) {
                        onEvent(HomeEvent.Paginate(destination))
                    }
                }
            }
        }
    }, onRefresh = ::onRefresh)
}