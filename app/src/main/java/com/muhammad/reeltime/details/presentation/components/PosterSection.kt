package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import com.muhammad.reeltime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.APIConstants.IMAGE_BASE_URL

@Composable
fun PosterSection(media: Media) {
    val context = LocalContext.current
    val imageUrl = "${IMAGE_BASE_URL}${media.posterPath}"
    val imageState = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(imageUrl).size(Size.ORIGINAL).build()
    ).state
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
            .padding(start = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        MovieImage(
            imageState = imageState,
            description = stringResource(R.string.poster, media.title),
            icon = ImageVector.vectorResource(R.drawable.not_supported)
        )
    }
}