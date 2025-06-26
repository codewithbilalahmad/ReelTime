package com.muhammad.reeltime.details.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.details.presentation.components.FavouritesSection
import com.muhammad.reeltime.details.presentation.components.InfoSection
import com.muhammad.reeltime.details.presentation.components.OverviewSection
import com.muhammad.reeltime.details.presentation.components.PosterSection
import com.muhammad.reeltime.details.presentation.components.SimilarSection
import com.muhammad.reeltime.details.presentation.components.VideoSection
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.utils.ObserveAsEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun DetailScreen(
    navHostController: NavHostController, mediaId: Int,
    viewModel: DetailViewModel
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.navigateToWatchVideoChannel) { event ->
        when (event) {
            true -> {
                navHostController.navigate(Destinations.WatchVideoScreen)
            }

            false -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_video_is_available_at_the_moment),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailEvent.StartLoading(mediaId))
    }
    DetailScreenContent(
        navHostController = navHostController,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    navHostController: NavHostController,
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
) {
    if (state.media == null) {
        SomethingWentWrong()
    } else {
        val scope = rememberCoroutineScope()
        var isRefreshing by remember { mutableStateOf(false) }
        fun onRefresh() = scope.launch {
            isRefreshing = true
            delay(1500)
            onEvent(DetailEvent.Refresh)
            isRefreshing = false
        }

        val refreshState = rememberPullToRefreshState()
        GradientBackground {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = ::onRefresh,
                state = refreshState
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullToRefresh(
                            isRefreshing = isRefreshing,
                            onRefresh = ::onRefresh,
                            state = refreshState
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        VideoSection(media = state.media, onEvent = onEvent)
                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            PosterSection(media = state.media)
                            Spacer(Modifier.width(12.dp))
                            InfoSection(media = state.media, readable = state.readableTime)
                        }
                        Spacer(Modifier.height(12.dp))
                        OverviewSection(media = state.media)
                        SimilarSection(navHostController = navHostController, state = state)
                    }
                    FavouritesSection(
                        state = state, onEvent = onEvent, modifier = Modifier
                            .align(
                                Alignment.BottomCenter
                            )
                            .navigationBarsPadding()
                    )
                }
            }
        }
    }
}

@Composable
fun SomethingWentWrong() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}