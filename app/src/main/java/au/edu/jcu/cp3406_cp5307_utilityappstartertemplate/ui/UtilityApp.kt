package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.location.LocationRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UtilityApp(
    goReadyViewModel: GoReadyViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Utility") }
    val uiState = goReadyViewModel.uiState

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationRepository = remember { LocationRepository(context) }

    suspend fun loadCurrentLocationWeather() {
        try {
            val location = locationRepository.getCurrentLocation()

            if (location != null) {
                goReadyViewModel.loadWeatherForCurrentLocation(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracyMeters = location.accuracyMeters
                )
            } else {
                goReadyViewModel.handleLocationUnavailable()
            }
        } catch (e: SecurityException) {
            goReadyViewModel.handleLocationUnavailable()
        } catch (e: Exception) {
            goReadyViewModel.handleLocationUnavailable()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            coroutineScope.launch {
                loadCurrentLocationWeather()
            }
        } else {
            goReadyViewModel.handleLocationUnavailable()
        }
    }

    fun requestCurrentLocationWeather() {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            coroutineScope.launch {
                loadCurrentLocationWeather()
            }
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(uiState.useCurrentLocation) {
        if (uiState.useCurrentLocation) {
            while (true) {
                requestCurrentLocationWeather()
                delay(CURRENT_LOCATION_REFRESH_INTERVAL_MS)
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
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
                    refreshCount = uiState.refreshCount,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    currentLatitude = uiState.currentLatitude,
                    currentLongitude = uiState.currentLongitude,
                    locationAccuracyMeters = uiState.locationAccuracyMeters,
                    onRefresh = {
                        goReadyViewModel.refreshAdvice()

                        if (uiState.useCurrentLocation) {
                            requestCurrentLocationWeather()
                        }
                    }
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
                    useCurrentLocation = uiState.useCurrentLocation,
                    onUseCurrentLocationChange = { enabled ->
                        goReadyViewModel.setUseCurrentLocation(enabled)
                    },
                    locationMessage = uiState.locationMessage
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

private const val CURRENT_LOCATION_REFRESH_INTERVAL_MS = 15 * 60 * 1000L