package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherCities
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.CitySelectionCard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.SettingSwitchRow

@Composable
fun SettingsScreen(
    selectedCity: String,
    onCityChange: (String) -> Unit,
    useFahrenheit: Boolean,
    onUnitChange: (Boolean) -> Unit,
    showDetails: Boolean,
    onShowDetailsChange: (Boolean) -> Unit,
    detailedAdvice: Boolean,
    onAdviceModeChange: (Boolean) -> Unit,
    expandAdviceCard: Boolean,
    onExpandAdviceCardChange: (Boolean) -> Unit,
    backgroundMusicEnabled: Boolean,
    onBackgroundMusicChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 6.dp,
                bottom = 150.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CitySelectionCard(
            cities = WeatherCities.supportedCities,
            selectedCity = selectedCity,
            onCitySelected = onCityChange
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SettingSwitchRow(
                title = "Use Fahrenheit",
                description = "Show temperature in Fahrenheit instead of Celsius.",
                checked = useFahrenheit,
                onCheckedChange = onUnitChange
            )

            SettingSwitchRow(
                title = "Show details",
                description = "Show rain chance, UV index and wind speed.",
                checked = showDetails,
                onCheckedChange = onShowDetailsChange
            )

            SettingSwitchRow(
                title = "Detailed advice",
                description = "Show longer go-out advice on the main screen.",
                checked = detailedAdvice,
                onCheckedChange = onAdviceModeChange
            )

            SettingSwitchRow(
                title = "Expand advice card",
                description = "Hide the animation card and show advice across the full row.",
                checked = expandAdviceCard,
                onCheckedChange = onExpandAdviceCardChange
            )

            SettingSwitchRow(
                title = "Background music",
                description = "Play soft background music while using the app.",
                checked = backgroundMusicEnabled,
                onCheckedChange = onBackgroundMusicChange
            )
        }
    }
}