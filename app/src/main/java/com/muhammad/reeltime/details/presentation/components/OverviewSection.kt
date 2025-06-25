package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.muhammad.reeltime.R
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.muhammad.reeltime.home.domain.model.Media

@Composable
fun OverviewSection(media: Media) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        if (media.tagLine.isNotEmpty()) {
            Text(
                text = "\"${media.tagLine}\"",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)
            )
        }
        if (media.overview.isNotEmpty()) {
            Text(
                text = stringResource(R.string.overview),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(text = media.overview, style = MaterialTheme.typography.bodyMedium)
        }
    }
}