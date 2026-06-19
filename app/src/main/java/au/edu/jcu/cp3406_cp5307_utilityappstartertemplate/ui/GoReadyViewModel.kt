package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoReadyViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var uiState by mutableStateOf(GoReadyUiState())
        private set

    private var refreshJob: Job? = null

    init {
        refreshSelectedCityWeather()
    }

    fun selectCity(city: String) {
        if (city == uiState.selectedCity) return

        uiState = uiState.copy(selectedCity = city)
        refreshSelectedCityWeather()
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

    fun setExpandAdviceCard(enabled: Boolean) {
        uiState = uiState.copy(
            expandAdviceCard = enabled
        )
    }

    fun setBackgroundMusicEnabled(enabled: Boolean) {
        uiState = uiState.copy(
            backgroundMusicEnabled = enabled
        )
    }

    fun refreshWeather() {
        uiState = uiState.copy(
            refreshCount = uiState.refreshCount + 1
        )

        refreshSelectedCityWeather()
    }

    private fun refreshSelectedCityWeather() {
        val cityToLoad = uiState.selectedCity

        // Cancel the previous request before starting a new one.
        // This avoids older weather results replacing the latest selected city.
        refreshJob?.cancel()

        refreshJob = viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val weather = withContext(Dispatchers.IO) {
                    weatherRepository.getWeather(cityToLoad)
                }

                // Only update the UI if the user has not changed city during the request.
                if (uiState.selectedCity == cityToLoad) {
                    uiState = uiState.copy(
                        weather = weather,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                if (uiState.selectedCity == cityToLoad) {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Unable to update live weather. Please check your internet connection."
                    )
                }
            }
        }
    }

    override fun onCleared() {
        refreshJob?.cancel()
        super.onCleared()
    }
}