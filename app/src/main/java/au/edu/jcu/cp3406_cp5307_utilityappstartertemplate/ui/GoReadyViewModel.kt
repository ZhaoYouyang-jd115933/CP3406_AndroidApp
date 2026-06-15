package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoReadyViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private var autoRefreshJob: Job? = null

    var uiState by mutableStateOf(GoReadyUiState())
        private set

    init {
        refreshWeather()
        startAutoRefresh()
    }

    fun selectCity(city: String) {
        uiState = uiState.copy(selectedCity = city)
        refreshWeather()
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
        refreshWeather()
    }

    private fun refreshWeather() {
        val cityToLoad = uiState.selectedCity

        viewModelScope.launch {
            loadWeather(cityToLoad)
        }
    }

    private fun startAutoRefresh() {
        autoRefreshJob?.cancel()

        autoRefreshJob = viewModelScope.launch {
            while (true) {
                delay(AUTO_REFRESH_INTERVAL_MS)
                loadWeather(uiState.selectedCity)
            }
        }
    }

    private suspend fun loadWeather(city: String) {
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        try {
            val weather = withContext(Dispatchers.IO) {
                weatherRepository.getWeather(city)
            }

            if (uiState.selectedCity == city) {
                uiState = uiState.copy(
                    weather = weather,
                    isLoading = false,
                    errorMessage = null
                )
            }
        } catch (e: Exception) {
            uiState = uiState.copy(
                isLoading = false,
                errorMessage = "Weather update failed. Showing last available data."
            )
        }
    }

    companion object {
        private const val AUTO_REFRESH_INTERVAL_MS = 15 * 60 * 1000L
    }
}