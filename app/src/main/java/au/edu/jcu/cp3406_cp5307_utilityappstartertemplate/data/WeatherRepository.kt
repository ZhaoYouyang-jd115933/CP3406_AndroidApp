package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network.RetrofitInstance
import kotlin.math.roundToInt

class WeatherRepository {

    suspend fun getWeather(city: String): WeatherSnapshot {
        val location = getCityLocation(city)

        return getWeatherForCoordinates(
            latitude = location.latitude,
            longitude = location.longitude,
            locationName = city
        )
    }

    suspend fun getWeatherForCoordinates(
        latitude: Double,
        longitude: Double,
        locationName: String = "Current location"
    ): WeatherSnapshot {
        val response = RetrofitInstance.weatherApi.getForecast(
            latitude = latitude,
            longitude = longitude
        )

        val currentHourIndex = findCurrentHourIndex(
            currentTime = response.current.time,
            hourlyTimes = response.hourly.time
        )

        val rainChance = response.hourly.precipitationProbability
            .getOrNull(currentHourIndex) ?: 0

        val uvIndex = response.hourly.uvIndex
            .getOrNull(currentHourIndex)
            ?.roundToInt() ?: 0

        return WeatherSnapshot(
            city = locationName,
            temperatureC = response.current.temperatureC.roundToInt(),
            rainChance = rainChance,
            uvIndex = uvIndex,
            windKmh = response.current.windKmh.roundToInt()
        )
    }

    private fun getCityLocation(city: String): CityLocation {
        return when (city) {
            "Sydney" -> CityLocation(latitude = -33.8688, longitude = 151.2093)
            "Tokyo" -> CityLocation(latitude = 35.6762, longitude = 139.6503)
            "London" -> CityLocation(latitude = 51.5072, longitude = -0.1276)
            else -> CityLocation(latitude = 1.3521, longitude = 103.8198)
        }
    }

    private fun findCurrentHourIndex(
        currentTime: String,
        hourlyTimes: List<String>
    ): Int {
        val currentHour = currentTime.take(13)
        val index = hourlyTimes.indexOfFirst { it.take(13) == currentHour }
        return if (index >= 0) index else 0
    }
}

private data class CityLocation(
    val latitude: Double,
    val longitude: Double
)