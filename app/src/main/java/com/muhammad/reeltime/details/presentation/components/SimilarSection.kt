package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.details.presentation.detail.DetailState
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.MediaImage

@Composable
fun SimilarSection(navHostController: NavHostController,state : DetailState) {
    val mediaList = state.similarList.take(10)
    if (mediaList.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 22.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.similar),style = MaterialTheme.typography.titleSmall
                )
                TextButton(onClick = {
                    navHostController.navigate(Destinations.SimilarScreen)
                }) {
                    Text(text = stringResource(R.string.see_all),style = MaterialTheme.typography.bodyMedium)
                }
            }

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