package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoReadyViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    var uiState by mutableStateOf(GoReadyUiState())
        private set

    init {
        refreshSelectedCityWeather()
    }

    fun selectCity(city: String) {
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

    fun refreshAdvice() {
        uiState = uiState.copy(refreshCount = uiState.refreshCount + 1)
        refreshSelectedCityWeather()
    }

    fun setBackgroundMusicEnabled(enabled: Boolean) {
        uiState = uiState.copy(
            backgroundMusicEnabled = enabled
        )
    }

    private fun refreshSelectedCityWeather() {
        val cityToLoad = uiState.selectedCity

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val weather = withContext(Dispatchers.IO) {
                    weatherRepository.getWeather(cityToLoad)
                }

                if (uiState.selectedCity == cityToLoad) {
                    uiState = uiState.copy(
                        weather = weather,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Unable to update live weather. Please check your internet connection."
                )
            }
        }
    }
}