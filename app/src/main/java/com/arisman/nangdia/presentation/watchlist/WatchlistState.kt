package com.arisman.nangdia.presentation.watchlist

import com.arisman.nangdia.domain.model.WatchlistMedia

data class WatchlistState(
    val items: List<WatchlistMedia> = emptyList(),
    val isLoading: Boolean = false
)

