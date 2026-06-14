package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

data class WeatherSnapshot(
    val city: String,
    val temperatureC: Int,
    val rainChance: Int,
    val uvIndex: Int,
    val windKmh: Int
)