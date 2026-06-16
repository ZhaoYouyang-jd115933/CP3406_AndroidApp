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
        uiState = uiState.copy(
            selectedCity = city,
            useCurrentLocation = false,
            currentLatitude = null,
            currentLongitude = null,
            locationAccuracyMeters = null,
            locationMessage = "Using selected city weather."
        )

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

    fun setUseCurrentLocation(useCurrentLocation: Boolean) {
        uiState = uiState.copy(
            useCurrentLocation = useCurrentLocation,
            locationMessage = if (useCurrentLocation) {
                "Using current location weather."
            } else {
                "Using selected city weather."
            }
        )

        if (!useCurrentLocation) {
            uiState = uiState.copy(
                currentLatitude = null,
                currentLongitude = null,
                locationAccuracyMeters = null
            )

            refreshSelectedCityWeather()
        }
    }

    fun refreshAdvice() {
        uiState = uiState.copy(refreshCount = uiState.refreshCount + 1)

        if (!uiState.useCurrentLocation) {
            refreshSelectedCityWeather()
        }
    }

    fun loadWeatherForCurrentLocation(
        latitude: Double,
        longitude: Double,
        accuracyMeters: Float?
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null,
                currentLatitude = latitude,
                currentLongitude = longitude,
                locationAccuracyMeters = accuracyMeters,
                locationMessage = "Updating weather for your current location..."
            )

            try {
                val weather = withContext(Dispatchers.IO) {
                    weatherRepository.getWeatherForCoordinates(
                        latitude = latitude,
                        longitude = longitude,
                        locationName = "Current location"
                    )
                }

                uiState = uiState.copy(
                    weather = weather,
                    isLoading = false,
                    errorMessage = null,
                    currentLatitude = latitude,
                    currentLongitude = longitude,
                    locationAccuracyMeters = accuracyMeters,
                    locationMessage = "Weather updated using current location."
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Current location weather failed. Showing last available data."
                )
            }
        }
    }

    fun handleLocationUnavailable() {
        uiState = uiState.copy(
            useCurrentLocation = false,
            isLoading = false,
            currentLatitude = null,
            currentLongitude = null,
            locationAccuracyMeters = null,
            locationMessage = "Location unavailable. Using selected city instead."
        )

        refreshSelectedCityWeather()
    }

    fun refreshSelectedCityWeather() {
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

                if (!uiState.useCurrentLocation && uiState.selectedCity == cityToLoad) {
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
    }
}