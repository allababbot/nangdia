package com.arisman.nangdia.presentation.awards

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.MdbListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AwardsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedAwardCategory: String = "Oscar",
    val selectedYear: Int = 2024,
    val availableCategories: List<String> = listOf("Oscar", "Emmy", "Golden Globe", "BAFTA", "SAG Awards"),
    val availableYears: List<Int> = (2024 downTo 1929).toList(),
    val winners: List<String> = emptyList()
)

@HiltViewModel
class AwardsViewModel @Inject constructor(
    private val repository: MdbListRepository
) : ViewModel() {

    private val _state = mutableStateOf(AwardsState())
    val state: State<AwardsState> = _state

    init {
        loadAwards()
    }

    fun onCategorySelected(category: String) {
        _state.value = _state.value.copy(selectedAwardCategory = category)
        loadAwards()
    }

    fun onYearSelected(year: Int) {
        _state.value = _state.value.copy(selectedYear = year)
        loadAwards()
    }

    private fun loadAwards() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // TODO: Replace with actual repository call once awards listing API integration is complete
                // Sample data for initial implementation
                val sampleWinners = when (_state.value.selectedAwardCategory) {
                    "Oscar" -> listOf("Oppenheimer (2024)", "Everything Everywhere All at Once (2023)", "CODA (2022)")
                    "Emmy" -> listOf("Succession (2023)", "The Bear (2023)", "Ted Lasso (2022)")
                    "Golden Globe" -> listOf("Oppenheimer (2024)", "The Fabelmans (2023)", "The Power of the Dog (2022)")
                    "BAFTA" -> listOf("Oppenheimer (2024)", "All Quiet on the Western Front (2023)", "The Power of the Dog (2022)")
                    "SAG Awards" -> listOf("Oppenheimer (2024)", "Everything Everywhere All at Once (2023)", "CODA (2022)")
                    else -> emptyList()
                }
                _state.value = _state.value.copy(
                    isLoading = false,
                    winners = sampleWinners
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load awards data"
                )
            }
        }
    }
}
