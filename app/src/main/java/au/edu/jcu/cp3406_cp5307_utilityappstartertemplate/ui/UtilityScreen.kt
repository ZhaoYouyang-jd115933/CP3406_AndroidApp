package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.formatTemperature
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getAdviceUiModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.getGoOutStatus
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.AdviceTextCard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.AdviceVisualCard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.GoReadyBrandHeader
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.HeroWeatherIcon
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components.WeatherGaugeCard

@Composable
fun UtilityScreen(
    weather: WeatherSnapshot,
    useFahrenheit: Boolean,
    showDetails: Boolean,
    detailedAdvice: Boolean,
    expandAdviceCard: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit
) {
    // Convert raw weather values into user-facing text and advice models.
    // This keeps the UI focused on display instead of duplicating business logic.
    val temperatureText = formatTemperature(weather.temperatureC, useFahrenheit)
    val status = getGoOutStatus(weather)
    val adviceUi = getAdviceUiModel(weather, detailedAdvice)

    // Clamp gauge progress values so unusual API data cannot draw outside the valid range.
    val rainProgress = (weather.rainChance / 100f).coerceIn(0f, 1f)
    val uvProgress = (weather.uvIndex / 11f).coerceIn(0f, 1f)
    val windProgress = (weather.windKmh / 60f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            // Scrolling keeps the main utility screen usable on smaller devices.
            .verticalScroll(rememberScrollState())
            .padding(
                start = 18.dp,
                end = 18.dp,
                top = 18.dp,
                bottom = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GoReadyBrandHeader(
            modifier = Modifier.fillMaxWidth()
        )

        if (isLoading) {
            Text(
                text = "Updating live weather...",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF4F5A70)
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Hero card gives the user the most important weather information at a glance.
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF9CC8FF),
                                Color(0xFF83B4F5),
                                Color(0xFF78A9EE)
                            )
                        )
                    )
                    .padding(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(0.58f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = weather.city,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.95f),
                        maxLines = 1
                    )

                    Text(
                        text = status,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            lineHeight = 30.sp,
                            letterSpacing = 0.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2
                    )

                    Text(
                        text = temperatureText,
                        style = MaterialTheme.typography.displaySmall.copy(
                            lineHeight = 42.sp,
                            letterSpacing = 0.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )

                    Text(
                        text = "Current temperature",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 1
                    )
                }

                Text(
                    text = "Live",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = Color.White.copy(alpha = 0.22f),
                            shape = RoundedCornerShape(999.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )

                HeroWeatherIcon(
                    weather = weather,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp, top = 8.dp)
                        .size(112.dp)
                )
            }
        }

        // The advice area can switch between a compact visual layout and an expanded text layout.
        // This behavior is controlled by the settings screen.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            if (expandAdviceCard) {
                AdviceTextCard(
                    advice = adviceUi,
                    showDetail = detailedAdvice,
                    expanded = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp)
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdviceTextCard(
                        advice = adviceUi,
                        showDetail = detailedAdvice,
                        modifier = Modifier.weight(1.55f)
                    )

                    AdviceVisualCard(
                        advice = adviceUi,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Weather details are optional so the main screen can stay simple when users prefer less detail.
        if (showDetails) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFF6F8FC)
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
                                Color(0xFFE0F7FA),
                                Color(0xFFB2EBF2)
                            ),
                            arcGradient = listOf(
                                Color(0xFFF0FDFF),
                                Color(0xFFCFFAFE),
                                Color(0xFF67E8F9)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        WeatherGaugeCard(
                            title = "UV",
                            valueText = weather.uvIndex.toString(),
                            label = "index",
                            progress = uvProgress,
                            cardGradient = listOf(
                                Color(0xFFFFF1F2),
                                Color(0xFFFFC7C2)
                            ),
                            arcGradient = listOf(
                                Color(0xFFFFF7F7),
                                Color(0xFFFFDAD6),
                                Color(0xFFFFA8A0)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        WeatherGaugeCard(
                            title = "Wind",
                            valueText = "${weather.windKmh}",
                            label = "km/h",
                            progress = windProgress,
                            cardGradient = listOf(
                                Color(0xFFF3E8FF),
                                Color(0xFFD8B4FE)
                            ),
                            arcGradient = listOf(
                                Color(0xFFFAF5FF),
                                Color(0xFFE9D5FF),
                                Color(0xFFC4B5FD)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Manual refresh keeps the interaction simple and predictable for a utility app.
        Button(
            onClick = onRefresh,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD6E8FF),
                contentColor = Color(0xFF1E3A5F)
            )
        ) {
            Text("Refresh Weather")
        }
    }
}


