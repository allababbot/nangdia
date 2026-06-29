package com.arisman.nangdia.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.arisman.nangdia.util.RatingFormatUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String, String, String) -> Unit,
    viewModel: PersonDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        state.person?.let { person ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                ) {
                    AsyncImage(
                        model = person.profilePath,
                        contentDescription = person.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.08f),
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-54).dp)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(26.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.34f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Text(
                                text = person.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )

                            person.knownFor?.let {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (!person.biography.isNullOrEmpty()) {
                        EditorialSection(
                            title = "Biography",
                            subtitle = "A closer look at the person behind the work."
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(22.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.28f)
                                )
                            ) {
                                Text(
                                    text = person.biography,
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 22.sp,
                                    modifier = Modifier.padding(18.dp)
                                )
                            }
                        }
                    }

                    if (person.combinedCredits.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(22.dp))
                        EditorialSection(
                            title = "Known For",
                            subtitle = "Essential films and series tied to this profile."
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(end = 16.dp)
                            ) {
                                items(person.combinedCredits) { media ->
                                    KnownForCard(
                                        title = media.title,
                                        posterPath = media.posterPath,
                                        rating = media.rating,
                                        year = media.year,
                                        onClick = {
                                            onNavigateToDetail(
                                                media.id.toString(),
                                                canonicalMediaType(media.mediaType),
                                                "tmdb"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (state.isLoading) {
            Surface(
                modifier = Modifier.align(Alignment.Center),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(18.dp))
            }
        }

        if (state.error.isNotBlank()) {
            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f))
            ) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
                )
            }
        }

        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .statusBarsPadding()
                .background(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
                    CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }
}

@Composable
private fun EditorialSection(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun KnownForCard(
    title: String,
    posterPath: String?,
    rating: Double?,
    year: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(136.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.28f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Box {
                AsyncImage(
                    model = posterPath,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
                rating?.let {
                    val score = (it * 10).toInt()
                    val ratingColor = RatingFormatUtil.getRatingColor(score)
                    Surface(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.76f),
                        shape = RoundedCornerShape(999.dp),
                        border = BorderStroke(1.dp, ratingColor.copy(alpha = 0.22f)),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "$score%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = ratingColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            year?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
