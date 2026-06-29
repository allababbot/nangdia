package com.arisman.nangdia.presentation.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import com.arisman.nangdia.domain.model.MediaSearchResult
import kotlinx.coroutines.flow.catch

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {
    
    init {
        getCalendarReleases()
    }

    private val _state = mutableStateOf(CalendarState())
    val state: State<CalendarState> = _state

    fun onDateSelected(date: LocalDate) {
        _state.value = _state.value.copy(selectedDate = date)
    }

    fun changeMonth(months: Long) {
        _state.value = _state.value.copy(
            selectedDate = _state.value.selectedDate.plusMonths(months)
        )
    }

    private fun getCalendarReleases() {
        watchlistRepository.getWatchlist().onEach { watchlist ->
            val groupedReleases = watchlist
                .filter { it.released != null || it.releasedDigital != null }
                .flatMap { media ->
                    val events = mutableListOf<Pair<String, com.arisman.nangdia.domain.model.WatchlistMedia>>()
                    
                    media.released?.let {
                        events.add(it to media)
                    }
                    media.releasedDigital?.let {
                        if (it != media.released) {
                            events.add(it to media)
                        }
                    }
                    events
                }
                .groupBy { it.first }
                .mapValues { entry -> 
                    entry.value.map { pair -> 
                        val media = pair.second
                        MediaSearchResult(
                            id = media.mediaId.toIntOrNull() ?: 0,
                            title = media.title,
                            poster = media.poster,
                            mediaType = media.mediaType,
                            year = media.year,
                            imdbId = if (media.provider == "imdb") media.mediaId else null,
                            tmdbId = if (media.provider == "tmdb") media.mediaId.toIntOrNull() else null,
                            traktId = if (media.provider == "trakt") media.mediaId.toIntOrNull() else null,
                            score = media.score
                        )
                    }.distinctBy { it.imdbId ?: it.tmdbId?.toString() ?: it.id.toString() } 
                }
                .toSortedMap()

            _state.value = _state.value.copy(
                releases = groupedReleases,
                isLoading = false
            )
        }.catch { e ->
            _state.value = _state.value.copy(
                error = e.message ?: "An unknown database error occurred",
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun formatDate(dateString: String): String {
        return try {
            val date = LocalDate.parse(dateString)
            date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault()))
        } catch (e: Exception) {
            dateString
        }
    }
    
    fun getMonthHeader(dateString: String): String {
        return try {
            val date = LocalDate.parse(dateString)
            date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))
        } catch (e: Exception) {
            "Other"
        }
    }
}
