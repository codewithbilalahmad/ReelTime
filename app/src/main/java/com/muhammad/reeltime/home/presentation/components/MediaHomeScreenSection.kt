package com.muhammad.reeltime.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.presentation.HomeState
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.MediaImage

@Composable
fun MediaHomeScreenSection(
    destination: Destinations,
    state: HomeState,
    navHostController: NavHostController,
) {
    var mediaList by remember { mutableStateOf<List<Media>>(emptyList()) }
    var title by remember { mutableStateOf("") }
    when (destination) {
        Destinations.TrendingScreen -> {
            mediaList = state.trendingList.take(10)
            title = stringResource(R.string.trending)
        }

        Destinations.TvScreen -> {
            mediaList = state.tvList.take(10)
            title = stringResource(R.string.tv_series)
        }

        Destinations.MovieScreen -> {
            mediaList = state.moviesList.take(10)
            title = stringResource(R.string.movies)
        }

        else -> Unit
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            if (mediaList.isNotEmpty()) {
                TextButton(onClick = {
                    navHostController.navigate(destination)
                }) {
                    Text(
                        text = stringResource(R.string.see_all),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
        if (mediaList.isEmpty()) {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                repeat(6) {
                    Spacer(Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                MaterialTheme.colorScheme.inverseOnSurface
                            )
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
        } else {
            LazyRow {
                items(mediaList.size) { index ->
                    var paddingEnd = 0.dp
                    if (index == mediaList.size - 1) {
                        paddingEnd = 16.dp
                    }
                    MediaImage(
                        media = mediaList[index],
                        navHostController = navHostController,
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .padding(start = 16.dp, end = paddingEnd)
                    )
                }
            }
        }
    }
}