package com.arisman.nangdia.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaRating
import com.arisman.nangdia.domain.model.MediaStream
import com.arisman.nangdia.domain.model.MediaCast
import com.arisman.nangdia.util.MediaPresentationUtil
import com.arisman.nangdia.util.NumberFormatUtil
import com.arisman.nangdia.util.RatingFormatUtil
import com.arisman.nangdia.ui.theme.BrandInk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String, String, String) -> Unit,
    onNavigateToPerson: (Int) -> Unit,
    onNavigateToSeasons: (String, String, String) -> Unit,
    viewModel: MediaDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isBookmarked by viewModel.isBookmarked
    var selectedRating by remember { mutableStateOf<MediaRating?>(null) }
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Main scrollable content
        state.mediaDetail?.let { detail ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Backdrop Image with Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                ) {
                    AsyncImage(
                        model = detail.backdrop ?: detail.poster,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.16f),
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.06f),
                                        MaterialTheme.colorScheme.background
                                    ),
                                    startY = 0f,
                                    endY = 900f
                                )
                            )
                    )
                }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .offset(y = (-64).dp)
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.32f)
                            )
                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            // Poster Image
                            AsyncImage(
                                model = detail.poster,
                                contentDescription = detail.title,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = detail.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                val metaList = mutableListOf<String>()
                                detail.year?.let { metaList.add(it.toString()) }
                                if (detail.mediaType == "show") {
                                    if (detail.seasonsCount != null && detail.seasonsCount > 0) metaList.add("${detail.seasonsCount} Seasons")
                                    if (detail.episodesCount != null && detail.episodesCount > 0) metaList.add("${detail.episodesCount} Episodes")
                                } else {
                                    detail.runtimeMinutes?.let { if (it > 0) metaList.add("$it min") }
                                }
                                detail.certification?.let { metaList.add(it) }
                                detail.ageRating?.let { metaList.add(it) }

                                Text(
                                    text = metaList.joinToString(" • "),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (detail.genres.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        contentPadding = PaddingValues(end = 16.dp)
                                    ) {
                                        items(detail.genres) { genre ->
                                            GenreChip(genre)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                detail.score?.let {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "$it%",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = " MDBList",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Awards Badge
                                if (detail.awardWins > 0 || detail.awardNominations > 0) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.55f)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "🏆",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = buildString {
                                                    if (detail.awardWins > 0) append("${detail.awardWins} Wins")
                                                    if (detail.awardWins > 0 && detail.awardNominations > 0) append(" & ")
                                                    if (detail.awardNominations > 0) append("${detail.awardNominations} Nominations")
                                                },
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Ratings Section
                        if (detail.ratings.isNotEmpty()) {
                            SectionHeader(
                                title = "Ratings",
                                subtitle = "A cross-platform snapshot from the main review sources."
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            PanelSurface {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp)
                                ) {
                                    val sortedRatings = detail.ratings.sortedBy { rating ->
                                        val sourceName = rating.source.lowercase().replace(" ", "")
                                        when {
                                            sourceName == "imdb" -> 1
                                            sourceName == "rotten" || sourceName == "tomatoes" || sourceName == "rottentomatoes" || sourceName == "rt" -> 2
                                            sourceName == "popcorn" || sourceName == "tomatoesaudience" || sourceName == "rottentomatoesaudience" -> 3
                                            sourceName.contains("metacriticuser") -> 4
                                            sourceName.contains("metacritic") -> 5
                                            sourceName == "tmdb" || sourceName == "themoviedb" -> 6
                                            sourceName == "trakt" || sourceName == "trakttv" -> 7
                                            sourceName == "letterboxd" -> 8
                                            sourceName == "rogerebert" -> 9
                                            sourceName == "myanimelist" || sourceName == "mal" -> 10
                                            else -> 99
                                        }
                                    }
                                    items(sortedRatings) { rating ->
                                        RatingChip(
                                            rating = rating,
                                            onClick = { selectedRating = rating }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Storyline Section
                        SectionHeader(
                            title = "Storyline",
                            subtitle = "A quick read on the premise, tone, and setup."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PanelSurface {
                            Text(
                                text = detail.description ?: "No description available.",
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // TV Series Seasons Navigation
                        if (detail.mediaType == "show" || detail.mediaType == "tv") {
                            Button(
                                onClick = {
                                    val detail = state.mediaDetail
                                    val safeId = detail?.imdbId
                                        ?: detail?.tmdbId?.toString()
                                        ?: state.mediaId
                                        ?: "-"

                                    val safeType = canonicalMediaType(
                                        detail?.mediaType?.takeIf { it.isNotEmpty() }
                                            ?: state.mediaDetail?.mediaType
                                            ?: "show"
                                    )
                                    val safeProvider = if (detail?.imdbId != null) "imdb"
                                        else if (detail?.tmdbId != null) "tmdb"
                                        else state.provider
                                        ?: "imdb"

                                    onNavigateToSeasons(
                                        Uri.encode(safeId),
                                        Uri.encode(safeType),
                                        Uri.encode(safeProvider)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "View Seasons & Episodes",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (detail.seasonsCount != null) {
                                        Text(
                                            text = " (${detail.seasonsCount} Seasons)",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Cast Section
                        if (detail.cast.isNotEmpty()) {
                            SectionHeader(
                                title = "Cast",
                                subtitle = "Key faces and performances tied to this title."
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            PanelSurface {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp)
                                ) {
                                    items(detail.cast) { castMember ->
                                        CastItem(
                                            castMember = castMember,
                                            onClick = { onNavigateToPerson(castMember.id) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Information Section
                        val isMovie = detail.mediaType != "show" && detail.mediaType != "tv"
                        val hasReleaseInfo = detail.released != null || detail.releasedDigital != null
                        val hasStats = (detail.budget != null && detail.budget > 0) || (detail.revenue != null && detail.revenue > 0) || detail.popularity != null || detail.rank != null
                        val hasBasics = detail.language != null || detail.country != null || detail.status != null
                        val hasExtra = detail.productionCompanies.isNotEmpty()

                        if (hasReleaseInfo || hasStats || hasBasics || hasExtra) {
                            SectionHeader(
                                title = "Information",
                                subtitle = "Release, production, and context in a cleaner reference panel."
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                ),
                                shape = RoundedCornerShape(18.dp),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.24f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Release dates
                                    if (isMovie) {
                                        detail.released?.let {
                                            InfoRow(label = "Theatrical Release", value = MediaPresentationUtil.formatLongIndonesianDate(it))
                                        }
                                        detail.releasedDigital?.let {
                                            InfoRow(label = "Streaming Release", value = MediaPresentationUtil.formatLongIndonesianDate(it))
                                        }
                                    } else {
                                        detail.released?.let {
                                            InfoRow(label = "First Aired", value = MediaPresentationUtil.formatLongIndonesianDate(it))
                                        }
                                    }

                                    // "Kapan Streaming?" untuk film yang masih tayang
                                    if (isMovie) {
                                        detail.releasedDigital?.let { digitalDate ->
                                            InfoRow(
                                                label = "Kapan Streaming?",
                                                value = "Rilis digital pada ${MediaPresentationUtil.formatLongIndonesianDate(digitalDate)}"
                                            )
                                        } ?: detail.released?.let { releasedDate ->
                                            val inTheaters = try {
                                                val today = java.time.LocalDate.now()
                                                val released = java.time.LocalDate.parse(releasedDate)
                                                released.isAfter(today)
                                            } catch (_: Exception) {
                                                false
                                            }
                                            if (inTheaters) {
                                                InfoRow(
                                                    label = "Kapan Streaming?",
                                                    value = "Sedang tayang di bioskop"
                                                )
                                            }
                                        }
                                    }

                                    // Stats
                                    detail.rank?.let { InfoRow(label = "Global Rank", value = "#$it") }
                                    com.arisman.nangdia.util.PopularityFormatUtil.formatPopularityLabel(detail.popularity)?.let {
                                        InfoRow(label = "Popularity", value = it)
                                    }
                                    detail.budget?.let { if (it > 0) InfoRow(label = "Budget", value = NumberFormatUtil.formatCurrency(it) ?: "-") }
                                    detail.revenue?.let { if (it > 0) InfoRow(label = "Revenue", value = NumberFormatUtil.formatCurrency(it) ?: "-") }

                                    // Details
                                    detail.language?.let { InfoRow(label = "Language", value = it.uppercase()) }
                                    detail.country?.let { InfoRow(label = "Country", value = it.uppercase()) }
                                    detail.status?.let { InfoRow(label = "Status", value = it.replaceFirstChar { c -> c.uppercase() }) }

                                    // Production
                                    if (detail.productionCompanies.isNotEmpty()) {
                                        InfoRow(
                                            label = "Production",
                                            value = detail.productionCompanies.joinToString(", ")
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }



                        // Keywords Section
                        if (detail.keywords.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            SectionHeader(
                                title = "Keywords",
                                subtitle = "Themes and descriptors shaping the title's identity."
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            PanelSurface {
                                Text(
                                    text = detail.keywords.take(20).joinToString(", "),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Parental Guide Section — placed last
                                                SectionHeader(
                                                    title = "Parental Guide",
                                                    subtitle = "A clearer summary of category severity at a glance."
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                                PanelSurface {
                                                    if (detail.parentalGuideCategories.isEmpty()) {
                                                        Text(
                                                            text = "Tidak tersedia",
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            modifier = Modifier.padding(16.dp),
                                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                                        )
                                                    } else {
                                                        Column(
                                                            verticalArrangement = Arrangement.spacedBy(8.dp),
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(16.dp)
                                                        ) {
                                                            detail.parentalGuideCategories.forEach { pgCategory ->
                                                                val severityColor = RatingFormatUtil.getSeverityColor(pgCategory.severity)
                                                                Surface(
                                                                    shape = RoundedCornerShape(16.dp),
                                                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.58f),
                                                                    border = BorderStroke(
                                                                        1.dp,
                                                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.18f)
                                                                    )
                                                                ) {
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .fillMaxWidth()
                                                                            .padding(horizontal = 12.dp, vertical = 10.dp),
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Surface(
                                                                            shape = RoundedCornerShape(999.dp),
                                                                            color = severityColor.copy(alpha = 0.16f),
                                                                            border = BorderStroke(1.dp, severityColor.copy(alpha = 0.35f)),
                                                                            modifier = Modifier.size(12.dp)
                                                                        ) {}
                                                                        Spacer(modifier = Modifier.width(12.dp))
                                                                        Text(
                                                                            text = pgCategory.category
                                                                                .replace("_", " ")
                                                                                .lowercase()
                                                                                .replaceFirstChar { it.uppercaseChar() },
                                                                            style = MaterialTheme.typography.bodyMedium,
                                                                            fontWeight = FontWeight.Medium,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = pgCategory.severity
                                                                                .lowercase()
                                                                                .replaceFirstChar { it.uppercaseChar() },
                                                                            style = MaterialTheme.typography.labelMedium,
                                                                            color = severityColor,
                                                                            fontWeight = FontWeight.Bold
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(24.dp))


                        // Recommendations Section
                        if (detail.recommendations.isNotEmpty()) {
                            SectionHeader(
                                title = "More Like This",
                                subtitle = "Nearby recommendations if you want to keep the mood going."
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            PanelSurface {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp)
                                ) {
                                    items(detail.recommendations) { recommendation ->
                                        RecommendationItem(
                                            recommendation = recommendation,
                                            onClick = {
                                                val id = recommendation.imdbId ?: recommendation.tmdbId?.toString() ?: recommendation.id.toString()
                                                val provider = if (recommendation.imdbId != null) "imdb" else "tmdb"
                                                onNavigateToDetail(id, recommendation.mediaType, provider)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(18.dp))
                    }
                }
            }

            state.error?.let { error ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 16.dp)
                        )
                    }
                }
            }

            state.mediaDetail?.let { detail ->
                if (selectedRating != null) {
                    RatingDetailDialog(
                        rating = selectedRating!!,
                        detail = detail,
                        onDismiss = { selectedRating = null }
                    )
                }
            }

        // Floating overlay navigation bar at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
                    shape = CircleShape
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            Box {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
                        shape = CircleShape
                    )
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(if (isBookmarked) "Remove from Watchlist" else "Add to Watchlist")
                        },
                        onClick = {
                            viewModel.toggleWatchlist()
                            menuExpanded = false
                        }
                    )
                    state.mediaDetail?.let { detail ->
                        if (detail.trailer != null) {
                            DropdownMenuItem(
                                text = { Text("Watch Trailer") },
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detail.trailer))
                                    context.startActivity(intent)
                                    menuExpanded = false
                                }
                            )
                        }
                        if (detail.mdblistId != null || detail.imdbId != null) {
                            DropdownMenuItem(
                                text = { Text("Open in MDBList") },
                                onClick = {
                                    val mediaBase = if (detail.mediaType == "show") "show" else "movie"
                                    val url = if (detail.mdblistId != null)
                                        "https://mdblist.com/$mediaBase/${detail.mdblistId}"
                                    else
                                        "https://mdblist.com/$mediaBase/${detail.imdbId}"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                    menuExpanded = false
                                }
                            )
                        }
                        if (detail.rtSlug != null) {
                            DropdownMenuItem(
                                text = { Text("Open in Rotten Tomatoes") },
                                onClick = {
                                    val mediaBase = if (detail.mediaType == "show") "tv" else "m"
                                    val url = "https://www.rottentomatoes.com/$mediaBase/${detail.rtSlug}"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetadataItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
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
    }
}

@Composable
private fun PanelSurface(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.24f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
    ) {
        Column(content = content)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.45f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(0.55f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun RatingChip(
    rating: MediaRating,
    onClick: () -> Unit
) {
    val score = RatingFormatUtil.getNormalizedScore(rating)
    val color = RatingFormatUtil.getRatingColor(score)

    Card(
        onClick = onClick,
        modifier = Modifier.width(88.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.28f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = RatingFormatUtil.shortenSourceName(rating.source).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = BrandInk,
                maxLines = 1
            )
            Text(
                text = score?.let { "$it%" } ?: "-",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = BrandInk
            )
        }
    }
}

@Composable
fun RatingDetailDialog(
    rating: MediaRating,
    detail: MediaDetail,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val normalizedScore = RatingFormatUtil.getNormalizedScore(rating)
    val color = RatingFormatUtil.getRatingColor(normalizedScore)
    val formattedRating = RatingFormatUtil.getFormattedRating(rating)
    val sourceUrl = RatingFormatUtil.getSourceUrl(rating, detail)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = rating.source.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = normalizedScore?.let { "$it%" } ?: "-",
                        color = BrandInk,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Original Rating",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formattedRating,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                rating.votes?.let { votes ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "from ${NumberFormatUtil.formatVotes(votes)} votes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            if (sourceUrl != null) {
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
                    context.startActivity(intent)
                    onDismiss()
                }) {
                    Text("View on Website")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun GenreChip(genre: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(percent = 50),
        modifier = Modifier.height(24.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = genre,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RecommendationItem(
    recommendation: com.arisman.nangdia.domain.model.MediaSearchResult,
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
                    model = recommendation.poster,
                    contentDescription = recommendation.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
                recommendation.score?.let { score ->
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
                text = recommendation.title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${MediaPresentationUtil.toDisplayMediaType(recommendation.mediaType)} • ${recommendation.year ?: "Unknown"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CastItem(
    castMember: MediaCast,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(92.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.68f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = castMember.profilePath,
                    contentDescription = castMember.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = castMember.name,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = castMember.character ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
