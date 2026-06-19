package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network.WeatherApiService
import kotlin.math.roundToInt

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherSnapshot
}

object WeatherCities {
    val supportedCities = listOf(
        "Singapore",
        "Bergen",
        "Darwin",
        "Wellington",
        "Dubai",
        "Turpan",
        "Reykjavik",
        "Tokyo",
        "London",
        "Ushuaia"
    )
}

class OpenMeteoWeatherRepository(
    private val weatherApi: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(city: String): WeatherSnapshot {
        val location = getCityLocation(city)

        return getWeatherForCoordinates(
            latitude = location.latitude,
            longitude = location.longitude,
            locationName = city
        )
    }

    private suspend fun getWeatherForCoordinates(
        latitude: Double,
        longitude: Double,
        locationName: String
    ): WeatherSnapshot {
        val response = weatherApi.getForecast(
            latitude = latitude,
            longitude = longitude
        )

        val currentHourIndex = findCurrentHourIndex(
            currentTime = response.current.time,
            hourlyTimes = response.hourly.time
        )

        val rainChance = response.hourly.precipitationProbability
            .getOrNull(currentHourIndex)
            ?: 0

        val uvIndex = response.hourly.uvIndex
            .getOrNull(currentHourIndex)
            ?.roundToInt()
            ?: 0

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
            "Singapore" -> CityLocation(latitude = 1.3521, longitude = 103.8198)

            // Rain-prone city, useful for umbrella advice when rain probability is high.
            "Bergen" -> CityLocation(latitude = 60.3913, longitude = 5.3221)

            // Tropical city, useful for high UV or heat advice.
            "Darwin" -> CityLocation(latitude = -12.4634, longitude = 130.8456)

            // Windy city, useful for wind-care advice.
            "Wellington" -> CityLocation(latitude = -41.2865, longitude = 174.7762)

            // Hot city, useful for hydration advice.
            "Dubai" -> CityLocation(latitude = 25.2048, longitude = 55.2708)

            // Very hot city in China, useful for hydration advice.
            "Turpan" -> CityLocation(latitude = 42.9513, longitude = 89.1895)

            // Cold city, useful for layer-up advice.
            "Reykjavik" -> CityLocation(latitude = 64.1466, longitude = -21.9426)

            "Tokyo" -> CityLocation(latitude = 35.6762, longitude = 139.6503)
            "London" -> CityLocation(latitude = 51.5072, longitude = -0.1276)
            "Ushuaia" -> CityLocation(latitude = -54.8019, longitude = -68.3030)

            else -> CityLocation(latitude = 1.3521, longitude = 103.8198)
        }
    }

    private fun findCurrentHourIndex(
        currentTime: String,
        hourlyTimes: List<String>
    ): Int {
        val currentHour = currentTime.take(13)
        val index = hourlyTimes.indexOfFirst { hourlyTime ->
            hourlyTime.take(13) == currentHour
        }

        return if (index >= 0) index else 0
    }
}

private data class CityLocation(
    val latitude: Double,
    val longitude: Double
)
