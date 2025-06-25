package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.muhammad.reeltime.details.presentation.detail.DetailEvent
import com.muhammad.reeltime.details.presentation.detail.DetailState
import com.muhammad.reeltime.ui.components.ReelTimeDialog
import com.muhammad.reeltime.R
import com.muhammad.reeltime.ui.components.ReelTimeActionButton
import com.muhammad.reeltime.ui.components.ReelTimeOutlinedActionButton

@Composable
fun FavouritesSection(modifier: Modifier, state: DetailState, onEvent: (DetailEvent) -> Unit) {
    if (state.showDialog) {
        FavouriteDialog(state = state, onEvent = onEvent)
    }
    state.media?.let { media ->
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            FloatingActionButton(onClick = {
                onEvent(DetailEvent.ToggleDialog(isRemoveFromFavouriteDialog = true))
            }) {
                val icon =
                    ImageVector.vectorResource(if (media.isLiked) R.drawable.heart_filled else R.drawable.heart_outlined)
                Icon(imageVector = icon, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            FloatingActionButton(modifier = Modifier.weight(1f), onClick = {
                onEvent(DetailEvent.ToggleDialog(isRemoveFromFavouriteDialog = false))
            }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = ImageVector.vectorResource(if (media.isBookmarked) R.drawable.bookmark else R.drawable.bookmark_outlined)
                    val text = if(media.isBookmarked) stringResource(R.string.unbookmark) else stringResource(R.string.bookmark)
                    Icon(imageVector = icon, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(text = text)
                }
            }
        }
    }
}

@Composable
fun FavouriteDialog(state: DetailState, onEvent: (DetailEvent) -> Unit) {
    val title = if (state.isRemoveFromFavouriteDialog) stringResource(R.string.remove_from_favorites) else stringResource(
            R.string.remote_from_bookmarks
        )
    ReelTimeDialog(onDismiss = {

    }, title = title, primaryButton = {
        ReelTimeActionButton(isLoading = false, text = stringResource(R.string.yes), onClick = {
            if (state.isRemoveFromFavouriteDialog) {
                onEvent(DetailEvent.ToggleLike)
            } else {
                onEvent(DetailEvent.ToggleBookmark)
            }
        }, modifier = Modifier.weight(1f))
    }, secondaryButton = {
        ReelTimeOutlinedActionButton(isLoading = false, text = stringResource(R.string.no) ,modifier = Modifier.weight(1f)) {
            onEvent(DetailEvent.ToggleDialog(state.isRemoveFromFavouriteDialog))
        }
    })
}