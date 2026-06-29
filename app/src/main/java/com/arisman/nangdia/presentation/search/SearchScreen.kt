package com.arisman.nangdia.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.presentation.search.components.MediaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (MediaSearchResult) -> Unit,
    onNavigateToAdvancedSearch: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Find movies and shows through a calmer, more focused search flow.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { viewModel.onQueryChange(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search movies or shows...", maxLines = 1) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (state.query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onQueryChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear search")
                            }
                        }
                    },
                    shape = RoundedCornerShape(22.dp),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f)
                    )
                )

                IconButton(
                    onClick = onNavigateToAdvancedSearch,
                    modifier = Modifier
                        .size(52.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                            shape = RoundedCornerShape(18.dp)
                        ),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Advanced Search",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (state.query.isEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Try a title, cast name, or jump into advanced filters.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.results.isEmpty() && !state.isLoading && state.error == null) {
                val title = if (state.query.isNotEmpty()) "No Matches Yet" else "Ready to Explore"
                val message = if (state.query.isNotEmpty()) {
                    "No results found for \"${state.query}\""
                } else {
                    "Find your next movie night, hidden gem, or series to binge."
                }
                val subMessage = if (state.query.isNotEmpty()) {
                    "Try a shorter title, another spelling, or widen the search with filters."
                } else {
                    "Start with a title you know, then explore deeper from the results."
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = subMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.results, key = { it.id }) { media ->
                    MediaItem(
                        media = media,
                        cast = state.castMap[media.id.toString()] ?: emptyList(),
                        onClick = { onNavigateToDetail(media) }
                    )
                }
            }

            if (state.isLoading) {
                Surface(
                    modifier = Modifier.align(Alignment.Center),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(18.dp)
                    )
                }
            }

            state.error?.let { error ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.error.copy(alpha = 0.35f)
                    )
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 18.dp)
                    )
                }
            }
        }
    }
}
