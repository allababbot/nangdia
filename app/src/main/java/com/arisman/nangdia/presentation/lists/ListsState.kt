package com.arisman.nangdia.presentation.lists

import com.arisman.nangdia.domain.model.MdbListMetadata
import com.arisman.nangdia.domain.model.MediaSearchResult

data class ListsState(
    val topLists: List<MdbListMetadata> = emptyList(),
    val isListsLoading: Boolean = false,
    val listsError: String? = null,
    
    val selectedList: MdbListMetadata? = null,
    val listItems: List<MediaSearchResult> = emptyList(),
    val isItemsLoading: Boolean = false,
    val itemsError: String? = null
)
