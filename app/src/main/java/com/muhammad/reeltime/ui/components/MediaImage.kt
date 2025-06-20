package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.utils.APIConstants.IMAGE_BASE_URL
import com.muhammad.reeltime.R

@Composable
fun MediaImage(
    modifier: Modifier = Modifier,
    media: Media,
    isPoster: Boolean = true,
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    val imageUrl = if (isPoster) {
        "${IMAGE_BASE_URL}${media.posterPath}"
    } else {
        "${IMAGE_BASE_URL}${media.backdropPath}"
    }
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(imageUrl).size(
            Size.ORIGINAL
        ).build()
    ).state
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable {
                navHostController.navigate(Destinations.CoreDetailsScreen(media.mediaId))
            }) {
        when (imageState) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.Center)
                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = imageState.painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    imageVector = ImageVector.vectorResource(R.drawable.not_supported)
                )
            }
        }
    }
}