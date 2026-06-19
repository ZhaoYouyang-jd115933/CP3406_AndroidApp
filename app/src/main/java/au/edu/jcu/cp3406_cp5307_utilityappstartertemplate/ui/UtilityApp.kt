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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme

@Composable
fun UtilityApp(
    goReadyViewModel: GoReadyViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Utility") }
    val uiState = goReadyViewModel.uiState

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
                    expandAdviceCard = uiState.expandAdviceCard,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    onRefresh = goReadyViewModel::refreshAdvice
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
                    onExpandAdviceCardChange = goReadyViewModel::setExpandAdviceCard
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

