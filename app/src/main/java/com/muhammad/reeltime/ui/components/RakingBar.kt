package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import kotlin.math.ceil
import kotlin.math.floor
import com.muhammad.reeltime.R

@Composable
fun RakingBar(
    modifier: Modifier = Modifier,
    starModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = rating.rem(1) != 0.0
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = starModifier,
                imageVector = ImageVector.vectorResource(R.drawable.star_filled),
                tint = starColor,
                contentDescription = null
            )
        }
        if(halfStar) {
            Icon(
                modifier = starModifier,
                imageVector = ImageVector.vectorResource(R.drawable.half_star),
                tint = starColor,
                contentDescription = null
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = starModifier,
                imageVector = ImageVector.vectorResource(R.drawable.star_outlined),
                tint = starColor,
                contentDescription = null
            )
        }
    }
}