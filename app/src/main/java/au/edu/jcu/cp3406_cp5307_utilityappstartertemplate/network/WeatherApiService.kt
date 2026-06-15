package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,wind_speed_10m,weather_code",
        @Query("hourly") hourly: String = "precipitation_probability,uv_index",
        @Query("forecast_days") forecastDays: Int = 1,
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}