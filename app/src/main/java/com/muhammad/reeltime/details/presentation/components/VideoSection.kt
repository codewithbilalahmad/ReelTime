package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.background
import com.muhammad.reeltime.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.muhammad.reeltime.details.presentation.detail.DetailEvent
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.APIConstants.IMAGE_BASE_URL

@Composable
fun VideoSection(media: Media, onEvent: (DetailEvent) -> Unit) {
    val context = LocalContext.current
    val imageUrl = "${IMAGE_BASE_URL}${media.backdropPath}"
    val imageState = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(imageUrl).size(Size.ORIGINAL).build()
    ).state
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onEvent(DetailEvent.NavigateToWatchVideo)
            }, shape = RoundedCornerShape(0.dp), elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MovieImage(imageState = imageState, description = "Backdrop")
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.play),
                contentDescription = null,
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}