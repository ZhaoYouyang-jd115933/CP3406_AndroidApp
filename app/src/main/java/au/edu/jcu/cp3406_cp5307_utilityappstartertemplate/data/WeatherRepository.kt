package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

class WeatherRepository {

    fun getWeather(city: String): WeatherSnapshot {
        return when (city) {
            "Sydney" -> WeatherSnapshot(
                city = "Sydney",
                temperatureC = 22,
                rainChance = 35,
                uvIndex = 6,
                windKmh = 18
            )

            "Tokyo" -> WeatherSnapshot(
                city = "Tokyo",
                temperatureC = 27,
                rainChance = 55,
                uvIndex = 7,
                windKmh = 10
            )

            "London" -> WeatherSnapshot(
                city = "London",
                temperatureC = 16,
                rainChance = 72,
                uvIndex = 3,
                windKmh = 22
            )

            else -> WeatherSnapshot(
                city = "Singapore",
                temperatureC = 30,
                rainChance = 68,
                uvIndex = 9,
                windKmh = 14
            )
        }
    }
}