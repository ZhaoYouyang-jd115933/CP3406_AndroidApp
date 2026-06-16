package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    useCurrentLocation: Boolean,
    onUseCurrentLocationChange: (Boolean) -> Unit,
    locationMessage: String?
) {
    val cities = listOf("Singapore", "Sydney", "Tokyo", "London")

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Weather Source",
                    style = MaterialTheme.typography.titleMedium
                )

                SettingSwitchRow(
                    title = "Use current location",
                    description = "Use your device location to update weather advice.",
                    checked = useCurrentLocation,
                    onCheckedChange = onUseCurrentLocationChange
                )

                if (locationMessage != null) {
                    Text(
                        text = locationMessage,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                HorizontalDivider()

                Text(
                    text = if (useCurrentLocation) {
                        "Manual city selection is disabled while current location is enabled."
                    } else {
                        "Choose a city manually when current location is off."
                    },
                    style = MaterialTheme.typography.bodySmall
                )

                cities.forEach { city ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !useCurrentLocation) {
                                onCityChange(city)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCity == city,
                            onClick = {
                                if (!useCurrentLocation) {
                                    onCityChange(city)
                                }
                            },
                            enabled = !useCurrentLocation
                        )

                        Text(text = city)
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Display Preferences",
                    style = MaterialTheme.typography.titleMedium
                )

                SettingSwitchRow(
                    title = "Use Fahrenheit",
                    description = "Show temperature in Fahrenheit instead of Celsius.",
                    checked = useFahrenheit,
                    onCheckedChange = onUnitChange
                )

                HorizontalDivider()

                SettingSwitchRow(
                    title = "Show details",
                    description = "Show rain chance, UV index and wind speed.",
                    checked = showDetails,
                    onCheckedChange = onShowDetailsChange
                )

                HorizontalDivider()

                SettingSwitchRow(
                    title = "Detailed advice",
                    description = "Show longer go-out advice on the main screen.",
                    checked = detailedAdvice,
                    onCheckedChange = onAdviceModeChange
                )
            }
        }
    }
}