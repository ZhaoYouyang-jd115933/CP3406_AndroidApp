package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.formatTemperature
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getGoOutStatus
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getStatusNote
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.WeatherGaugeCard
import androidx.compose.foundation.layout.height
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getAdviceUiModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.AdviceTextCard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.AdviceVisualCard

@Composable
fun UtilityScreen(
    weather: WeatherSnapshot,
    useFahrenheit: Boolean,
    showDetails: Boolean,
    detailedAdvice: Boolean,
    refreshCount: Int,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit
) {
    val temperatureText = formatTemperature(weather.temperatureC, useFahrenheit)
    val status = getGoOutStatus(weather)
    val statusNote = getStatusNote(weather)
    val adviceUi = getAdviceUiModel(weather, detailedAdvice)

    val rainProgress = weather.rainChance / 100f
    val uvProgress = weather.uvIndex / 11f
    val windProgress = weather.windKmh / 60f

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AdviceTextCard(
                advice = adviceUi,
                modifier = Modifier.weight(1.7f)
            )

            AdviceVisualCard(
                advice = adviceUi,
                modifier = Modifier.weight(1f)
            )
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
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Weather details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        WeatherGaugeCard(
                            title = "Rain",
                            valueText = "${weather.rainChance}%",
                            label = "chance",
                            progress = rainProgress,
                            cardGradient = listOf(
                                Color(0xFF93C5FD),
                                Color(0xFF1D4ED8)
                            ),
                            arcGradient = listOf(
                                Color(0xFFDBEAFE),
                                Color(0xFF93C5FD),
                                Color(0xFF3B82F6)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        WeatherGaugeCard(
                            title = "UV",
                            valueText = weather.uvIndex.toString(),
                            label = "index",
                            progress = uvProgress,
                            cardGradient = listOf(
                                Color(0xFFF9A8D4),
                                Color(0xFFDB2777)
                            ),
                            arcGradient = listOf(
                                Color(0xFFFCE7F3),
                                Color(0xFFF9A8D4),
                                Color(0xFFEC4899)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        WeatherGaugeCard(
                            title = "Wind",
                            valueText = "${weather.windKmh}",
                            label = "km/h",
                            progress = windProgress,
                            cardGradient = listOf(
                                Color(0xFFD8B4FE),
                                Color(0xFF7C3AED)
                            ),
                            arcGradient = listOf(
                                Color(0xFFF3E8FF),
                                Color(0xFFD8B4FE),
                                Color(0xFFA855F7)
                            ),
                            modifier = Modifier.weight(1f)
                        )
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


