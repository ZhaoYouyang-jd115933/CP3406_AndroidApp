package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.di

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.OpenMeteoWeatherRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network.RetrofitInstance
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.network.WeatherApiService

class AppContainer {

    // The API service is created once and reused across the app.
    // This keeps networking setup separate from the ViewModel.
    private val weatherApi: WeatherApiService by lazy {
        RetrofitInstance.weatherApi
    }

    // The repository is provided through the container instead of being created
    // directly inside the ViewModel. This supports simple dependency injection.
    val weatherRepository: WeatherRepository by lazy {
        OpenMeteoWeatherRepository(weatherApi)
    }
}