package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GoReadyViewModel : ViewModel() {

    var uiState by mutableStateOf(GoReadyUiState())
        private set

    fun selectCity(city: String) {
        uiState = uiState.copy(selectedCity = city)
    }

    fun setUseFahrenheit(useFahrenheit: Boolean) {
        uiState = uiState.copy(useFahrenheit = useFahrenheit)
    }

    fun setShowDetails(showDetails: Boolean) {
        uiState = uiState.copy(showDetails = showDetails)
    }

    fun setDetailedAdvice(detailedAdvice: Boolean) {
        uiState = uiState.copy(detailedAdvice = detailedAdvice)
    }

    fun refreshAdvice() {
        uiState = uiState.copy(refreshCount = uiState.refreshCount + 1)
    }
}