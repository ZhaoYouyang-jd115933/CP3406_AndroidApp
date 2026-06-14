package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.GoReadyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

data class WeatherSnapshot(
    val city: String,
    val temperatureC: Int,
    val rainChance: Int,
    val uvIndex: Int,
    val windKmh: Int
)

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

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
                    selectedCity = uiState.selectedCity,
                    useFahrenheit = uiState.useFahrenheit,
                    showDetails = uiState.showDetails,
                    detailedAdvice = uiState.detailedAdvice,
                    refreshCount = uiState.refreshCount,
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
                    onAdviceModeChange = goReadyViewModel::setDetailedAdvice
                )
            }
        }
    }
}

@Composable
fun UtilityScreen(
    selectedCity: String,
    useFahrenheit: Boolean,
    showDetails: Boolean,
    detailedAdvice: Boolean,
    refreshCount: Int,
    onRefresh: () -> Unit
) {

    val weather = getSampleWeather(selectedCity)
    val temperatureText = formatTemperature(weather.temperatureC, useFahrenheit)
    val status = getGoOutStatus(weather)
    val statusNote = getStatusNote(weather)
    val advice = getAdvice(weather, detailedAdvice)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "GoReady",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Quick daily go-out advice based on weather conditions.",
            style = MaterialTheme.typography.bodyMedium
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = status,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = statusNote,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Advice",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = advice,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (showDetails) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Weather details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    WeatherDetailRow(label = "Rain chance", value = "${weather.rainChance}%")
                    HorizontalDivider()
                    WeatherDetailRow(label = "UV index", value = weather.uvIndex.toString())
                    HorizontalDivider()
                    WeatherDetailRow(label = "Wind speed", value = "${weather.windKmh} km/h")
                }
            }
        }

        Button(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Advice")
        }

        Text(
            text = "Updated $refreshCount time(s) in this session",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SettingsScreen(
    selectedCity: String,
    onCityChange: (String) -> Unit,
    useFahrenheit: Boolean,
    onUnitChange: (Boolean) -> Unit,
    showDetails: Boolean,
    onShowDetailsChange: (Boolean) -> Unit,
    detailedAdvice: Boolean,
    onAdviceModeChange: (Boolean) -> Unit
) {
    val cities = listOf("Singapore", "Sydney", "Tokyo", "London")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Control what is shown on the GoReady main screen.",
            style = MaterialTheme.typography.bodyMedium
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                cities.forEach { city ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCityChange(city) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCity == city,
                            onClick = { onCityChange(city) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = city,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingSwitchRow(
                    title = "Use Fahrenheit",
                    description = "Change temperature from °C to °F.",
                    checked = useFahrenheit,
                    onCheckedChange = onUnitChange
                )

                HorizontalDivider()

                SettingSwitchRow(
                    title = "Show weather details",
                    description = "Display rain chance, UV index and wind speed.",
                    checked = showDetails,
                    onCheckedChange = onShowDetailsChange
                )

                HorizontalDivider()

                SettingSwitchRow(
                    title = "Detailed advice",
                    description = "Show a fuller going-out recommendation.",
                    checked = detailedAdvice,
                    onCheckedChange = onAdviceModeChange
                )
            }
        }
    }
}

@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SettingSwitchRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

fun getSampleWeather(city: String): WeatherSnapshot {
    return when (city) {
        "Sydney" -> WeatherSnapshot(
            city = "Sydney",
            temperatureC = 22,
            rainChance = 35,
            uvIndex = 6,
            windKmh = 18
        )

        "Tokyo" -> WeatherSnapshot(
            city = "Tokyo",
            temperatureC = 27,
            rainChance = 55,
            uvIndex = 7,
            windKmh = 10
        )

        "London" -> WeatherSnapshot(
            city = "London",
            temperatureC = 16,
            rainChance = 72,
            uvIndex = 3,
            windKmh = 22
        )

        else -> WeatherSnapshot(
            city = "Singapore",
            temperatureC = 30,
            rainChance = 68,
            uvIndex = 9,
            windKmh = 14
        )
    }
}

fun formatTemperature(temperatureC: Int, useFahrenheit: Boolean): String {
    return if (useFahrenheit) {
        val fahrenheit = temperatureC * 9 / 5 + 32
        "$fahrenheit°F"
    } else {
        "$temperatureC°C"
    }
}

fun getGoOutStatus(weather: WeatherSnapshot): String {
    return when {
        weather.rainChance >= 70 -> "Rain risk"
        weather.uvIndex >= 8 -> "Sun protection needed"
        weather.rainChance >= 50 -> "Moderate conditions"
        else -> "Good to go"
    }
}

fun getStatusNote(weather: WeatherSnapshot): String {
    return when {
        weather.rainChance >= 70 -> "High rain chance may affect outdoor plans."
        weather.uvIndex >= 8 -> "High UV conditions expected today."
        weather.rainChance >= 50 -> "Weather is manageable, but check before leaving."
        else -> "Conditions look suitable for normal outdoor plans."
    }
}

fun getAdvice(weather: WeatherSnapshot, detailedAdvice: Boolean): String {
    return if (detailedAdvice) {
        when {
            weather.rainChance >= 70 ->
                "Bring an umbrella and allow extra travel time. Outdoor plans may be affected by rain."

            weather.uvIndex >= 8 ->
                "Use sunscreen, drink water, and avoid staying outdoors too long around midday."

            weather.rainChance >= 50 ->
                "Carry a small umbrella and check the weather again before leaving."

            else ->
                "Conditions look suitable for going out. Normal outdoor plans should be fine."
        }
    } else {
        when {
            weather.rainChance >= 70 -> "Bring an umbrella."
            weather.uvIndex >= 8 -> "Use sunscreen."
            weather.rainChance >= 50 -> "Carry a small umbrella."
            else -> "Good to go."
        }
    }
}