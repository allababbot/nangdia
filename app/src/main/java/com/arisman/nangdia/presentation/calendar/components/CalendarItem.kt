package com.arisman.nangdia.presentation.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.util.MediaPresentationUtil
import com.arisman.nangdia.util.RatingFormatUtil

@Composable
fun CalendarItem(
    media: MediaSearchResult,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.36f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.26f),
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = media.poster,
            contentDescription = null,
            modifier = Modifier
                .width(54.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = media.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.42f)
                ) {
                    Text(
                        text = MediaPresentationUtil.toDisplayMediaType(media.mediaType),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                if (media.year != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = media.year.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            media.score?.let { score ->
                Spacer(modifier = Modifier.height(8.dp))
                val color = RatingFormatUtil.getRatingColor(score)
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = color.copy(alpha = 0.14f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.28f))
                ) {
                    Text(
                        text = "$score%",
                        style = MaterialTheme.typography.labelMedium,
                        color = color,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
