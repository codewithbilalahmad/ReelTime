package com.muhammad.reeltime.favourite.presentation.favourite_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.favourite.presentation.FavouriteEvent
import com.muhammad.reeltime.favourite.presentation.FavouriteState
import com.muhammad.reeltime.favourite.presentation.FavouriteViewModel
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.MediaImageAndTitle
import com.muhammad.reeltime.ui.components.NonFocusedTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteListScreen(
    navHostController: NavHostController, destination: Destinations,
    viewModel: FavouriteViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavouriteListScreenContent(
        destination = destination,
        state = state,
        onEvent = viewModel::onEvent,
        navHostController = navHostController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteListScreenContent(
    destination: Destinations,
    navHostController: NavHostController, state: FavouriteState, onEvent: (FavouriteEvent) -> Unit,
) {
    val title = when (destination) {
        Destinations.LikedListScreen -> stringResource(R.string.favorites)
        else -> stringResource(R.string.bookmarks)
    }
    val mediaList = when (destination) {
        Destinations.LikedListScreen -> state.likedList
        else -> state.bookmarkedList
    }
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    fun onRefresh() = scope.launch {
        isRefreshing = true
        delay(1500)
        onEvent(FavouriteEvent.Refresh)
        isRefreshing = false
    }

    val refreshState = rememberPullToRefreshState()
    GradientBackground(hasTopbar = true){
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            NonFocusedTopBar(navHostController = navHostController, title = title)
        }) { paddingValues ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = ::onRefresh,
                state = refreshState
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .pullToRefresh(
                            isRefreshing = isRefreshing,
                            state = refreshState,
                            onRefresh = ::onRefresh
                        )
                ) {
                    items(mediaList) { media ->
                        MediaImageAndTitle(media = media, navHostController = navHostController)
                    }
                }
            }
        }
    }
}