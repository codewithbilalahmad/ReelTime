package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.utils.APIConstants.IMAGE_BASE_URL
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.domain.usecase.GenreIdsToString

@Composable
fun MediaImageAndTitle(
    media: Media,
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    val imageUri = "${IMAGE_BASE_URL}${media.posterPath}"
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(imageUri).size(Size.ORIGINAL).build()
    ).state
    val defaultColor = MaterialTheme.colorScheme.primary
    var averageColor by remember {
        mutableStateOf(defaultColor)
    }
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                brush = Brush.verticalGradient(
                    listOf(MaterialTheme.colorScheme.secondaryContainer, averageColor)
                )
            )
            .clickable {
                navHostController.navigate(Destinations.CoreDetailsScreen(media.mediaId))
            }) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(6.dp)
        ) {
            when (imageState) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(8.dp)
                            .align(Alignment.Center)
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    val bitmap = imageState.result.drawable.toBitmap()
                    averageColor = AverageColor(bitmap.asImageBitmap())
                    Image(
                        painter = imageState.painter,
                        contentDescription = media.title, contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                else -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.not_supported),
                        contentDescription = media.title,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            text = media.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            color = Color.White,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = GenreIdsToString.genreIdsToString(media.genreIds),
            fontSize = 13.sp,
            maxLines = 1,
            color = Color.White.copy(0.7f),
            overflow = TextOverflow.Ellipsis
        )
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            RakingBar(starModifier = Modifier.size(18.dp), rating = media.voteAverage / 2)
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                overflow = TextOverflow.Ellipsis,
                color = Color.White.copy(0.7f),
                maxLines = 1, fontSize = 14.sp,
                text = (media.voteAverage / 2).toString().take(3)
            )
        }
    }
}