package com.arisman.nangdia.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val repository: MdbListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PersonDetailState())
    val state: State<PersonDetailState> = _state

    init {
        savedStateHandle.get<Int>("personId")?.let { personId ->
            getPersonDetail(personId)
        }
    }

    private fun getPersonDetail(personId: Int) {
        repository.getPersonDetail(personId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        person = result.data,
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Failed to load person details"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class PersonDetailState(
    val isLoading: Boolean = false,
    val person: com.arisman.nangdia.domain.model.PersonDetail? = null,
    val error: String = ""
)
