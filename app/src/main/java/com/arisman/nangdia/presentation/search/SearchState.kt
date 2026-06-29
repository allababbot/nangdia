package com.arisman.nangdia.presentation.search

import com.arisman.nangdia.domain.model.MediaSearchResult

data class SearchState(
    val query: String = "",
    val results: List<MediaSearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedType: String? = null,
    val selectedYear: String? = null,
    val isFiltersVisible: Boolean = false,
    val castMap: Map<String, List<String>> = emptyMap()
)
