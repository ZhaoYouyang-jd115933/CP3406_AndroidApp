package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import kotlinx.coroutines.launch

class GoReadyViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    var uiState by mutableStateOf(GoReadyUiState())
        private set

    init {
        loadWeather(uiState.selectedCity)
    }

    fun selectCity(city: String) {
        uiState = uiState.copy(selectedCity = city)
        loadWeather(city)
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
        loadWeather(uiState.selectedCity)
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val weather = weatherRepository.getWeather(city)

                uiState = uiState.copy(
                    weather = weather,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Weather update failed. Showing last available data."
                )
            }
        }
    }
}