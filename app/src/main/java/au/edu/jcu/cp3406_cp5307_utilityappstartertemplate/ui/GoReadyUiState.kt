package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot

data class GoReadyUiState(
    val selectedCity: String = "Singapore",
    val weather: WeatherSnapshot = WeatherSnapshot(
        city = "Singapore",
        temperatureC = 30,
        rainChance = 68,
        uvIndex = 9,
        windKmh = 14
    ),
    val useFahrenheit: Boolean = false,
    val showDetails: Boolean = true,
    val detailedAdvice: Boolean = true,
    val refreshCount: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)