package com.muhammad.reeltime.details.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.reeltime.R
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.home.domain.usecase.GenreIdsToString
import com.muhammad.reeltime.ui.components.RakingBar

@Composable
fun InfoSection(media: Media, readable: String) {
    Column(modifier = Modifier.padding(end = 8.dp, top = 30.dp)) {
        Text(
            text = media.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(12.dp))
        if (media.voteAverage != 0.0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RakingBar(
                    starModifier = Modifier.size(18.dp),
                    rating = media.voteAverage.div(2),
                    starColor = Color(0xFFf4cb45)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = media.voteAverage.div(2).toString().take(3),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(if (media.adult) R.string._18 else R.string._12),
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp)
                )
                .padding(4.dp), style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        if (media.releaseDate.isNotEmpty()) {
            Text(text = media.releaseDate, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
        }
        if (media.genreIds.isNotEmpty()) {
            Text(
                text = GenreIdsToString.genreIdsToString(media.genreIds),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
        }
        if(readable.isNotEmpty()){
            Text(text = readable, style = MaterialTheme.typography.bodyLarge)
        }
    }
}