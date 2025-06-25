package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.R
import com.muhammad.reeltime.main.navigation.Destinations

@Composable
fun AutoSwipeSection(
    title: String,
    showSeeAll: Boolean = false,destination : Destinations?=null,
    navHostController: NavHostController,
    mediaList: List<Media>,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp
            )
            if (showSeeAll) {
                TextButton(onClick = {
                    destination?.let { route ->
                        navHostController.navigate(destination)
                    }
                }) {
                    Text(
                        text = stringResource(R.string.see_all),
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }
        AutoImageSwipePager(navHostController = navHostController, mediaList = mediaList)
    }
}