package com.arisman.nangdia.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.presentation.search.components.MediaItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AdvancedSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (MediaSearchResult) -> Unit,
    viewModel: AdvancedSearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var showFilters by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (showFilters) {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.search()
                        showFilters = false
                    },
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    text = { Text("Apply & Search") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.34f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = "Advanced Search",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Build a more focused search with filters and sort rules.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(onClick = { viewModel.resetFilters() }) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Reset")
                            }
                            FilledTonalButton(onClick = { showFilters = !showFilters }) {
                                Icon(
                                    if (showFilters) Icons.Default.Search else Icons.Default.FilterList,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (showFilters) "View Results" else "Show Filters")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    if (showFilters) {
                        FiltersView(viewModel = viewModel)
                    } else {
                        ResultsView(
                            state = state,
                            onShowFilters = { showFilters = true },
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FiltersView(viewModel: AdvancedSearchViewModel) {
    val state = viewModel.state.value

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FilterSection(
                title = "Content Type",
                subtitle = "Pick whether to explore movies or TV shows."
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = state.mediaType == "movie",
                        onClick = { viewModel.onMediaTypeChange("movie") },
                        label = { Text("Movies") }
                    )
                    FilterChip(
                        selected = state.mediaType == "tv",
                        onClick = { viewModel.onMediaTypeChange("tv") },
                        label = { Text("TV Shows") }
                    )
                }
            }
        }

        item {
            FilterSection(
                title = "Sort By",
                subtitle = "Choose what should rise to the top first."
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val sorts = listOf(
                        "popularity.desc" to "Popularity",
                        "release_date.desc" to "Release Date",
                        "vote_average.desc" to "Rating",
                        "revenue.desc" to "Revenue"
                    )
                    sorts.forEach { (value, label) ->
                        FilterChip(
                            selected = state.sortBy == value,
                            onClick = { viewModel.onSortByChange(value) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }

        item {
            FilterSection(
                title = "Genres",
                subtitle = "Mix in one or more genres to narrow the pool."
            ) {
                val genres = if (state.mediaType == "movie") movieGenres else tvGenres
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    genres.forEach { (id, name) ->
                        FilterChip(
                            selected = state.selectedGenres.contains(id),
                            onClick = { viewModel.onGenreToggle(id) },
                            label = { Text(name) }
                        )
                    }
                }
            }
        }

        item {
            FilterSection(
                title = "Year Range",
                subtitle = "Optional: focus on a starting year, ending year, or both."
            ) {
                var yearStartText by remember(state.yearStart) { mutableStateOf(state.yearStart?.toString() ?: "") }
                var yearEndText by remember(state.yearEnd) { mutableStateOf(state.yearEnd?.toString() ?: "") }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = yearStartText,
                        onValueChange = {
                            yearStartText = it.filter(Char::isDigit)
                            viewModel.onYearStartChange(yearStartText.toIntOrNull())
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("From") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f)
                        )
                    )
                    OutlinedTextField(
                        value = yearEndText,
                        onValueChange = {
                            yearEndText = it.filter(Char::isDigit)
                            viewModel.onYearEndChange(yearEndText.toIntOrNull())
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("To") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f)
                        )
                    )
                }
            }
        }

        item {
            FilterSection(
                title = "Minimum Rating",
                subtitle = "Set the floor for results you want to keep."
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Threshold",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${state.minRating.toInt()}+",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Slider(
                    value = state.minRating,
                    onValueChange = { viewModel.onRatingChange(it) },
                    valueRange = 0f..10f,
                    steps = 9
                )
            }
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.28f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
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
}

@Composable
fun ResultsView(
    state: AdvancedSearchState,
    onShowFilters: () -> Unit,
    onNavigateToDetail: (MediaSearchResult) -> Unit
) {
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
    } else if (state.results.isEmpty() && state.error == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.28f))
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No Results Yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Adjust the filters to widen or redirect the search.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(onClick = onShowFilters) {
                        Text("Change Filters")
                    }
                }
            }
        }
    } else if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f))
            ) {
                Text(
                    state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.24f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Results (${state.results.size})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Refined picks based on your current filter stack.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            items(state.results) { result ->
                MediaItem(
                    media = result,
                    onClick = { onNavigateToDetail(result) }
                )
            }
        }
    }
}

private val movieGenres = listOf(
    28 to "Action", 12 to "Adventure", 16 to "Animation", 35 to "Comedy",
    80 to "Crime", 99 to "Documentary", 18 to "Drama", 10751 to "Family",
    14 to "Fantasy", 36 to "History", 27 to "Horror", 10402 to "Music",
    9648 to "Mystery", 10749 to "Romance", 878 to "Sci-Fi", 53 to "Thriller",
    10752 to "War", 37 to "Western"
)

private val tvGenres = listOf(
    10759 to "Action & Adventure", 16 to "Animation", 35 to "Comedy",
    80 to "Crime", 99 to "Documentary", 18 to "Drama", 10751 to "Family",
    10762 to "Kids", 9648 to "Mystery", 10765 to "Sci-Fi & Fantasy",
    10766 to "Soap", 10767 to "Talk", 10768 to "War & Politics", 37 to "Western"
)
