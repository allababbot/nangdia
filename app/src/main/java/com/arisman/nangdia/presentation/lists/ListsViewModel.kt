package com.arisman.nangdia.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: MdbListRepository
) : ViewModel() {

    private val _state = mutableStateOf(ListsState())
    val state: State<ListsState> = _state

    init {
        getDiscoverLists()
    }

    fun onListSelect(listMetadata: com.arisman.nangdia.domain.model.MdbListMetadata) {
        _state.value = _state.value.copy(
            selectedList = listMetadata,
            listItems = emptyList(),
            itemsError = null
        )
        getListItems(listMetadata.user, listMetadata.slug)
    }

    fun clearSelection() {
        _state.value = _state.value.copy(
            selectedList = null,
            listItems = emptyList()
        )
    }

    private fun getDiscoverLists() {
        repository.getDiscoverLists().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        topLists = result.data ?: emptyList(),
                        isListsLoading = false,
                        listsError = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isListsLoading = false,
                        listsError = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isListsLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListItems(username: String, slug: String) {
        repository.getMdbList(username, slug).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        listItems = result.data ?: emptyList(),
                        isItemsLoading = false,
                        itemsError = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isItemsLoading = false,
                        itemsError = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isItemsLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
