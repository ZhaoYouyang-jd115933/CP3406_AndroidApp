package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.formatTemperature
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getAdvice
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getGoOutStatus
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getStatusNote
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.WeatherDetailRow
import java.util.Locale

@Composable
fun UtilityScreen(
    weather: WeatherSnapshot,
    useFahrenheit: Boolean,
    showDetails: Boolean,
    detailedAdvice: Boolean,
    refreshCount: Int,
    isLoading: Boolean,
    errorMessage: String?,
    currentLatitude: Double?,
    currentLongitude: Double?,
    locationAccuracyMeters: Float?,
    onRefresh: () -> Unit
) {
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

        if (isLoading) {
            Text(
                text = "Updating live weather...",
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

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

                    if (currentLatitude != null && currentLongitude != null) {
                        HorizontalDivider()

                        Text(
                            text = "Detected location",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        WeatherDetailRow(
                            label = "Latitude",
                            value = String.format(Locale.US, "%.4f", currentLatitude)
                        )

                        HorizontalDivider()

                        WeatherDetailRow(
                            label = "Longitude",
                            value = String.format(Locale.US, "%.4f", currentLongitude)
                        )

                        if (locationAccuracyMeters != null) {
                            HorizontalDivider()

                            WeatherDetailRow(
                                label = "Location accuracy",
                                value = "±${locationAccuracyMeters.toInt()} m"
                            )
                        }
                    }
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

