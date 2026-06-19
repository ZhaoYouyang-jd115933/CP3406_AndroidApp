package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.di.AppContainer
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme

@Composable
fun UtilityApp() {
    // AppContainer keeps object creation in one place.
    // This supports simple dependency injection instead of creating the repository inside the ViewModel.
    val appContainer = remember { AppContainer() }

    val goReadyViewModel: GoReadyViewModel = viewModel(
        factory = GoReadyViewModelFactory(appContainer.weatherRepository)
    )

    UtilityAppContent(
        goReadyViewModel = goReadyViewModel
    )
}

@Composable
private fun UtilityAppContent(
    goReadyViewModel: GoReadyViewModel
) {
    var selectedTab by remember { mutableStateOf("Utility") }
    val uiState = goReadyViewModel.uiState
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // Background music is controlled from the app shell so it follows the whole app lifecycle.
    LaunchedEffect(uiState.backgroundMusicEnabled, isPreview) {
        if (!isPreview) {
            if (uiState.backgroundMusicEnabled) {
                BackgroundMusicManager.start(context)
            } else {
                BackgroundMusicManager.pause()
            }
        }
    }

    // Release the media player when the composable leaves the screen to avoid leaking resources.
    DisposableEffect(isPreview) {
        onDispose {
            if (!isPreview) {
                BackgroundMusicManager.release()
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Utility"
                        )
                    },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(
                    weather = uiState.weather,
                    useFahrenheit = uiState.useFahrenheit,
                    showDetails = uiState.showDetails,
                    detailedAdvice = uiState.detailedAdvice,
                    expandAdviceCard = uiState.expandAdviceCard,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    onRefresh = goReadyViewModel::refreshWeather
                )

                "Settings" -> SettingsScreen(
                    selectedCity = uiState.selectedCity,
                    onCityChange = goReadyViewModel::selectCity,
                    useFahrenheit = uiState.useFahrenheit,
                    onUnitChange = goReadyViewModel::setUseFahrenheit,
                    showDetails = uiState.showDetails,
                    onShowDetailsChange = goReadyViewModel::setShowDetails,
                    detailedAdvice = uiState.detailedAdvice,
                    onAdviceModeChange = goReadyViewModel::setDetailedAdvice,
                    expandAdviceCard = uiState.expandAdviceCard,
                    onExpandAdviceCardChange = goReadyViewModel::setExpandAdviceCard,
                    backgroundMusicEnabled = uiState.backgroundMusicEnabled,
                    onBackgroundMusicChange = goReadyViewModel::setBackgroundMusicEnabled
                )
            }
        }
    }
}

// A fake repository is used only for Compose Preview.
// This avoids making a real network request while previewing the UI in Android Studio.
private class PreviewWeatherRepository : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherSnapshot {
        return WeatherSnapshot(
            city = city,
            temperatureC = 30,
            rainChance = 68,
            uvIndex = 9,
            windKmh = 14
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        val previewViewModel = remember {
            GoReadyViewModel(PreviewWeatherRepository())
        }

        UtilityAppContent(
            goReadyViewModel = previewViewModel
        )
    }
}

