package com.arisman.nangdia.presentation.calendar

import com.arisman.nangdia.domain.model.MediaSearchResult
import java.time.LocalDate

data class CalendarState(
    val releases: Map<String, List<MediaSearchResult>> = emptyMap(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val error: String? = null
)
