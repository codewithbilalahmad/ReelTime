package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter

@Composable
fun MovieImage(
    imageState: AsyncImagePainter.State,
    description: String,
    icon: ImageVector? = null,
) {
    when (imageState) {
        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = imageState.painter,
                contentDescription = description,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        else -> {
            icon?.let { icon ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = description,
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.6f),
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}