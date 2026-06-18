package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot

fun formatTemperature(temperatureC: Int, useFahrenheit: Boolean): String {
    return if (useFahrenheit) {
        val fahrenheit = temperatureC * 9 / 5 + 32
        "$fahrenheit°F"
    } else {
        "$temperatureC°C"
    }
}

fun getGoOutStatus(weather: WeatherSnapshot): String {
    return when {
        weather.rainChance >= 60 -> "Rain risk"
        weather.uvIndex >= 6 -> "Sun protection"
        weather.windKmh >= 25 -> "Strong wind"
        weather.temperatureC >= 30 -> "Hot weather"
        weather.temperatureC <= 18 -> "Cool weather"
        else -> "Good to go"
    }
}