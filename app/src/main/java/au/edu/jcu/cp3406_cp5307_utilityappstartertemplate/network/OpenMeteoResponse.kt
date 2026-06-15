package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network

import com.google.gson.annotations.SerializedName

data class OpenMeteoResponse(
    val current: CurrentWeatherDto,
    val hourly: HourlyWeatherDto
)

data class CurrentWeatherDto(
    val time: String,

    @SerializedName("temperature_2m")
    val temperatureC: Double,

    @SerializedName("wind_speed_10m")
    val windKmh: Double,

    @SerializedName("weather_code")
    val weatherCode: Int
)

data class HourlyWeatherDto(
    val time: List<String>,

    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,

    @SerializedName("uv_index")
    val uvIndex: List<Double>
)