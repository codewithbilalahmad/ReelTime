package com.muhammad.reeltime.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.search.presentation.components.SearchMediaItem
import com.muhammad.reeltime.ui.components.FocusedTopBar
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navHostController: NavHostController, viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isPaginating by remember { mutableStateOf(false) }
    ObserveAsEvents(viewModel.navigateToDetailChannel) { mediaId ->
        navHostController.navigate(Destinations.DetailsScreen(mediaId))
    }
    GradientBackground(hasTopbar = true){
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            FocusedTopBar(searchState = state, onEvent = viewModel::onEvent)
        }) { paddingValues ->
            if(state.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            } else{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues
                ) {
                    items(state.searchList.size){index ->
                        val media = state.searchList[index]
                        SearchMediaItem(media = media, onClick = {
                            viewModel.onEvent(SearchEvent.OnSearchItemClick(media))
                        })
                        LaunchedEffect(index,isPaginating) {
                            if(!isPaginating && index >= state.searchList.size -1 && !state.isMoreSearchLoading){
                                isPaginating = true
                                viewModel.onEvent(SearchEvent.OnPaginate)
                                isPaginating = false
                            }
                        }
                    }
                    if(state.isMoreSearchLoading){
                        items(2){
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(16.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                        }
                    }
                }
            }
        }
    }
}