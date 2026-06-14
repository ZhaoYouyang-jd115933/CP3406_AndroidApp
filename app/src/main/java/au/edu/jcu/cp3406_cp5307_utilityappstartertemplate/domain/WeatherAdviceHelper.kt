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
        weather.rainChance >= 70 -> "Rain risk"
        weather.uvIndex >= 8 -> "Sun protection needed"
        weather.rainChance >= 50 -> "Moderate conditions"
        else -> "Good to go"
    }
}

fun getStatusNote(weather: WeatherSnapshot): String {
    return when {
        weather.rainChance >= 70 -> "High rain chance may affect outdoor plans."
        weather.uvIndex >= 8 -> "High UV conditions expected today."
        weather.rainChance >= 50 -> "Weather is manageable, but check before leaving."
        else -> "Conditions look suitable for normal outdoor plans."
    }
}

fun getAdvice(weather: WeatherSnapshot, detailedAdvice: Boolean): String {
    return if (detailedAdvice) {
        when {
            weather.rainChance >= 70 ->
                "Bring an umbrella and allow extra travel time. Outdoor plans may be affected by rain."

            weather.uvIndex >= 8 ->
                "Use sunscreen, drink water, and avoid staying outdoors too long around midday."

            weather.rainChance >= 50 ->
                "Carry a small umbrella and check the weather again before leaving."

            else ->
                "Conditions look suitable for going out. Normal outdoor plans should be fine."
        }
    } else {
        when {
            weather.rainChance >= 70 -> "Bring an umbrella."
            weather.uvIndex >= 8 -> "Use sunscreen."
            weather.rainChance >= 50 -> "Carry a small umbrella."
            else -> "Good to go."
        }
    }
}