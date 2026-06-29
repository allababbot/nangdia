package com.arisman.nangdia.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.util.MediaPresentationUtil
import com.arisman.nangdia.util.RatingFormatUtil

@Composable
fun HomeScreen(
    onNavigateToDetail: (MediaSearchResult) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                HomeHero()
            }

            item {
                SectionTitle(
                    title = "Popular Movies",
                    subtitle = "What viewers are watching right now."
                )
                if (state.isLoading && state.popularMovies.isEmpty()) {
                    HomeShimmerRow()
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(state.popularMovies) { movie ->
                            HomeMediaCard(media = movie, onClick = { onNavigateToDetail(movie) })
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))
                SectionTitle(
                    title = "Popular TV Shows",
                    subtitle = "Series momentum, trending picks, and easy binges."
                )
                if (state.isLoading && state.popularTvShows.isEmpty()) {
                    HomeShimmerRow()
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(state.popularTvShows) { tvShow ->
                            HomeMediaCard(media = tvShow, onClick = { onNavigateToDetail(tvShow) })
                        }
                    }
                }
            }

            item {
                if (state.isLoading && state.popularMovies.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(18.dp))
                        }
                    }
                }
            }

            item {
                state.error?.let {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.error.copy(alpha = 0.35f)
                        )
                    ) {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHero() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.34f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.42f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.04f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "NANGDIA",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A calmer home for what is trending, worth watching, and ready for your next pick.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)
                    )
                ) {
                Text(
                    text = "Fresh picks across movies and TV series",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
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
    }
}

@Composable
fun HomeMediaCard(
    media: MediaSearchResult,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(154.dp)
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
                    model = media.poster,
                    contentDescription = media.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
                media.score?.let { score ->
                    val ratingColor = RatingFormatUtil.getRatingColor(score)
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.82f),
                        border = BorderStroke(1.dp, ratingColor.copy(alpha = 0.28f)),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "$score%",
                            style = MaterialTheme.typography.labelMedium,
                            color = ratingColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = media.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.18f)
                    )
                ) {
                    Text(
                        text = "${MediaPresentationUtil.toDisplayMediaType(media.mediaType)} • ${media.year ?: "Unknown"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}
