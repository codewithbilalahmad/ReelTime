package com.muhammad.reeltime.favourite.presentation.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.favourite.presentation.FavouriteEvent
import com.muhammad.reeltime.favourite.presentation.FavouriteState
import com.muhammad.reeltime.favourite.presentation.FavouriteViewModel
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.AutoSwipeSection
import com.muhammad.reeltime.ui.components.GradientBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen(
    navHostController: NavHostController,
    viewModel: FavouriteViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavouriteScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        navHostController = navHostController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreenContent(
    navHostController: NavHostController, state: FavouriteState, onEvent: (FavouriteEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    fun onRefresh() = scope.launch {
        isRefreshing = true
        delay(1500)
        onEvent(FavouriteEvent.Refresh)
        isRefreshing = false
    }

    val refreshState = rememberPullToRefreshState()
    GradientBackground(hasTopbar = true) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites_and_bookmarks),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }) { paddingValues ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                state = refreshState,
                onRefresh = ::onRefresh
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = 20.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .pullToRefresh(
                            isRefreshing = isRefreshing,
                            onRefresh = ::onRefresh,
                            state = refreshState
                        )
                ) {
                    if (state.likedList.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.favorites),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Box(
                            modifier = Modifier
                                .height(220.dp)
                                .fillMaxWidth(0.9f)
                                .padding(top = 20.dp, bottom = 12.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .align(Alignment.CenterHorizontally)
                        )
                    } else {
                        AutoSwipeSection(
                            title = stringResource(R.string.favorites),
                            showSeeAll = true,
                            mediaList = state.likedList,
                            navHostController = navHostController,
                            destination = Destinations.LikedListScreen
                        )
                    }
                    Spacer(Modifier.height(50.dp))
                    if (state.bookmarkedList.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.bookmarks),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Box(
                            modifier = Modifier
                                .height(220.dp)
                                .fillMaxWidth(0.9f)
                                .padding(top = 20.dp, bottom = 12.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .align(Alignment.CenterHorizontally)
                        )
                    } else {
                        AutoSwipeSection(
                            title = stringResource(R.string.bookmarks),
                            showSeeAll = true,
                            mediaList = state.bookmarkedList,
                            navHostController = navHostController,
                            destination = Destinations.BookmarkedScreen
                        )
                    }
                }
            }
        }
    }
}