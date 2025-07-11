package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muhammad.reeltime.R
import com.muhammad.reeltime.main.navigation.Destinations

@Composable
fun NonFocusedSearchBar(name: String = "", navHostController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                navHostController.navigate(Destinations.SearchScreen)
            }
            .padding(start = 16.dp, end = 7.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.search),
            contentDescription = stringResource(R.string.search_a_movies_or_tv_series),
            tint = MaterialTheme.colorScheme.onBackground.copy(0.5f)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_a_movies_or_tv_series),
            color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
            modifier = Modifier.weight(1f)
        )
        if (name.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .alpha(0.8f)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
                    .clickable {
                        navHostController.navigate(Destinations.ProfileScreen)
                    }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(1),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 19.sp
                )
            }
        }
        Spacer(Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer
                )
                .clickable {
                    navHostController.navigate(Destinations.FavoritesScreen)
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.bookmark),
                contentDescription = stringResource(R.string.favorites_and_bookmarks),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}