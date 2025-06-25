package com.muhammad.reeltime.home.presentation

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.presentation.components.MediaHomeScreenSection
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.AutoSwipeSection
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.NonFocusedTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel, navHostController: NavHostController) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        navHostController = navHostController,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navHostController: NavHostController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    GradientBackground(hasTopbar = true){
        Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Destinations.CategoriesScreen)
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.category),
                    contentDescription = stringResource(R.string.categories)
                )
            }
        }, topBar = {
            NonFocusedTopBar(
                navHostController = navHostController,
                name = state.name
            )
        }) {paddingValues ->
            val scope = rememberCoroutineScope()
            var refreshing by remember { mutableStateOf(false) }
            fun onRefresh() = scope.launch {
                refreshing = true
                delay(1500)
                onEvent(HomeEvent.Refresh(Destinations.HomeScreen))
                refreshing = false
            }

            val refreshState = rememberPullToRefreshState()
            Box(
                modifier = Modifier
                    .fillMaxSize().padding(paddingValues = paddingValues)
                    .pullToRefresh(
                        isRefreshing = refreshing,
                        onRefresh = ::onRefresh,
                        state = refreshState
                    )
            ) {
                PullToRefreshBox(isRefreshing = refreshing, onRefresh = ::onRefresh) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        MediaHomeScreenSection(
                            destination = Destinations.TrendingScreen,
                            state = state,
                            navHostController = navHostController
                        )
                        Spacer(Modifier.height(8.dp))
                        if (state.specialList.isEmpty()) {
                            Text(
                                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                                text = stringResource(R.string.special),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                        } else {
                            AutoSwipeSection(
                                title = stringResource(R.string.special),
                                navHostController = navHostController,
                                mediaList = state.specialList
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        MediaHomeScreenSection(
                            destination = Destinations.TvScreen,
                            state = state,
                            navHostController = navHostController
                        )
                        Spacer(Modifier.height(16.dp))
                        MediaHomeScreenSection(
                            destination = Destinations.MovieScreen,
                            state = state,
                            navHostController = navHostController
                        )
                        Spacer(Modifier.height(90.dp))
                    }
                }
            }
        }
    }
}