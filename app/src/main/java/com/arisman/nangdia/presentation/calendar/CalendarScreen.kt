package com.arisman.nangdia.presentation.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.presentation.calendar.components.CalendarItem
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (MediaSearchResult) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val selectedDate = state.selectedDate
    val currentMonth = YearMonth.from(selectedDate)

    val daysInMonth = remember(currentMonth) {
        val firstDayOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val days = mutableListOf<LocalDate?>()
        for (i in 0 until firstDayOfWeek) days.add(null)
        for (i in 1..currentMonth.lengthOfMonth()) days.add(currentMonth.atDay(i))
        days
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
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
                    Text(
                        text = "Release Calendar",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Track release dates and browse what lands on each day.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.changeMonth(-1) },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Prev Month")
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Tap a date to explore releases",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { viewModel.changeMonth(1) },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next Month")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        val dayLabels = listOf("S", "M", "T", "W", "T", "F", "S")
                        dayLabels.forEach { label ->
                            Text(
                                text = label,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    daysInMonth.chunked(7).forEach { week ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            week.forEach { date ->
                                val isSelected = date == selectedDate
                                val isToday = date != null && date == LocalDate.now()
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .then(
                                            when {
                                                isSelected -> Modifier.background(MaterialTheme.colorScheme.primary)
                                                isToday -> Modifier.border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                                else -> Modifier
                                            }
                                        )
                                        .clickable(enabled = date != null) {
                                            if (date != null) viewModel.onDateSelected(date)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (date != null) {
                                        val hasReleases = state.releases.containsKey(date.toString())
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = date.dayOfMonth.toString(),
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                            )
                                            if (hasReleases) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(top = 2.dp)
                                                        .size(5.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                            if (isSelected) MaterialTheme.colorScheme.onPrimary
                                                            else MaterialTheme.colorScheme.primary
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            if (week.size < 7) {
                                repeat(7 - week.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.24f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.formatDate(selectedDate.toString()),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Everything currently scheduled for this date.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val dayReleases = state.releases[selectedDate.toString()] ?: emptyList()
            if (dayReleases.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.28f))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 22.dp, vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No Releases Scheduled",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Try another date to browse what is arriving around this period.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(dayReleases) { media ->
                        CalendarItem(
                            media = media,
                            onClick = { onNavigateToDetail(media) }
                        )
                    }
                }
            }
        }
    }
}
