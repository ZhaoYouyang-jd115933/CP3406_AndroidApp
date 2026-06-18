package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot

fun getAdviceUiModel(
    weather: WeatherSnapshot,
    detailedAdvice: Boolean
): AdviceUiModel {
    return when {
        weather.rainChance >= 60 -> {
            AdviceUiModel(
                type = AdviceType.UMBRELLA,
                headline = "Bring an umbrella",
                detail = if (detailedAdvice) {
                    "Allow extra travel time. Rain may affect outdoor plans."
                } else {
                    "Rain may affect outdoor plans."
                },
                visualLabel = "Umbrella"
            )
        }

        weather.uvIndex >= 6 -> {
            AdviceUiModel(
                type = AdviceType.SUNSCREEN,
                headline = "Use sunscreen",
                detail = if (detailedAdvice) {
                    "Avoid direct sun exposure around midday and protect your skin."
                } else {
                    "Protect your skin outdoors."
                },
                visualLabel = "Sunscreen"
            )
        }

        weather.windKmh >= 25 -> {
            AdviceUiModel(
                type = AdviceType.WIND_CARE,
                headline = "Be careful in strong wind",
                detail = if (detailedAdvice) {
                    "Secure loose items and take care when travelling outdoors."
                } else {
                    "Take care outdoors."
                },
                visualLabel = "Wind care"
            )
        }

        weather.temperatureC >= 30 -> {
            AdviceUiModel(
                type = AdviceType.HYDRATE,
                headline = "Drink more water",
                detail = if (detailedAdvice) {
                    "Stay hydrated and avoid prolonged outdoor activity in the heat."
                } else {
                    "Stay hydrated outdoors."
                },
                visualLabel = "Hydrate"
            )
        }

        weather.temperatureC <= 18 -> {
            AdviceUiModel(
                type = AdviceType.LAYER_UP,
                headline = "Wear an extra layer",
                detail = if (detailedAdvice) {
                    "Consider adding a light jacket or scarf before going out."
                } else {
                    "Dress a little warmer."
                },
                visualLabel = "Layer up"
            )
        }

        else -> {
            AdviceUiModel(
                type = AdviceType.READY,
                headline = "Good to go",
                detail = if (detailedAdvice) {
                    "Conditions look suitable for normal outdoor plans."
                } else {
                    "Conditions look suitable."
                },
                visualLabel = "Ready"
            )
        }
    }
}

