package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.reeltime.search.presentation.SearchEvent
import com.muhammad.reeltime.search.presentation.SearchState
import com.muhammad.reeltime.R

@Composable
fun FocusedTopBar(
    toolbarOffsetHeightPx: Int,
    searchState: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(74.dp)
            .offset {
                IntOffset(x = 0, y = toolbarOffsetHeightPx)
            }
    ) {
        SearchBar(state = searchState,onEvent = onEvent)
    }
}

@Composable
fun SearchBar(state: SearchState, onEvent: (SearchEvent) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }
    BasicTextField(
        value = state.searchQuery, onValueChange = { newQuery ->
            onEvent(SearchEvent.OnSearchQueryChange(newQuery))
        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .focusRequester(focusRequester), singleLine = true, cursorBrush = SolidColor(
            MaterialTheme.colorScheme.primary
        ), textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground, fontSize = 17.sp
        ), decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search),
                    contentDescription = stringResource(R.string.search_a_movies_or_tv_series),
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(28.dp)
                )
                Box(modifier = Modifier.weight(1f)) {
                    if (state.searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_a_movies_or_tv_series),
                            color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                            fontSize = 17.sp
                        )
                    }
                    innerTextField()
                }
                if (state.searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.cancel),
                        contentDescription = stringResource(R.string.clear_search),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(28.dp)
                            .clickable {
                                onEvent(SearchEvent.OnSearchQueryChange(""))
                            }
                    )
                }
            }
        }
    )
}