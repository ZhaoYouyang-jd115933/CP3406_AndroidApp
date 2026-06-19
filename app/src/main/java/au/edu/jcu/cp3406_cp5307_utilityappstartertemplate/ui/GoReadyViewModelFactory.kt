package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository

class GoReadyViewModelFactory(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // The factory creates GoReadyViewModel with a repository dependency.
        // This avoids creating the repository directly inside the ViewModel.
        if (modelClass.isAssignableFrom(GoReadyViewModel::class.java)) {
            return GoReadyViewModel(weatherRepository) as T
        }

        // This error helps identify incorrect ViewModel requests during development.
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}