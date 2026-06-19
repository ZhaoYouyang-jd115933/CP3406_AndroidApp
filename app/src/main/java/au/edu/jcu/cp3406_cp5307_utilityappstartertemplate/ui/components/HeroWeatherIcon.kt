package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherSnapshot

private enum class HeroWeatherType {
    RAIN,
    SUN,
    WIND,
    HOT,
    COOL,
    READY
}

@Composable
fun HeroWeatherIcon(
    weather: WeatherSnapshot,
    modifier: Modifier = Modifier.size(88.dp)
) {
    val type = when {
        weather.rainChance >= 60 -> HeroWeatherType.RAIN

        // UV index 6 or above should show a strong sun icon because sunscreen is recommended.
        weather.uvIndex >= 6 -> HeroWeatherType.SUN

        weather.windKmh >= 25 -> HeroWeatherType.WIND
        weather.temperatureC >= 30 -> HeroWeatherType.HOT
        weather.temperatureC <= 18 -> HeroWeatherType.COOL
        else -> HeroWeatherType.READY
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        fun drawSoftCloud() {
            // Soft shadow under the cloud makes the icon feel less flat.
            drawOval(
                color = Color(0xFF3F6FA8).copy(alpha = 0.18f),
                topLeft = Offset(w * 0.18f, h * 0.62f),
                size = Size(w * 0.62f, h * 0.18f)
            )

            // Cloud base.
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFEAF3FF)
                    )
                ),
                topLeft = Offset(w * 0.20f, h * 0.40f),
                size = Size(w * 0.62f, h * 0.30f),
                cornerRadius = CornerRadius(w * 0.16f, w * 0.16f)
            )

            // Cloud puffs create a softer and more recognizable cloud shape.
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color(0xFFE7F1FF)),
                    center = Offset(w * 0.35f, h * 0.39f),
                    radius = w * 0.22f
                ),
                radius = w * 0.20f,
                center = Offset(w * 0.36f, h * 0.42f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color(0xFFE7F1FF)),
                    center = Offset(w * 0.54f, h * 0.33f),
                    radius = w * 0.26f
                ),
                radius = w * 0.25f,
                center = Offset(w * 0.54f, h * 0.36f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color(0xFFE7F1FF)),
                    center = Offset(w * 0.70f, h * 0.45f),
                    radius = w * 0.20f
                ),
                radius = w * 0.18f,
                center = Offset(w * 0.69f, h * 0.46f)
            )
        }

        fun drawSun() {
            // This smaller sun is kept for the READY state, where the icon should feel calm.
            drawCircle(
                color = Color(0xFFFFD66B).copy(alpha = 0.25f),
                radius = w * 0.25f,
                center = Offset(w * 0.35f, h * 0.32f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFF4A8),
                        Color(0xFFFFB800)
                    ),
                    center = Offset(w * 0.32f, h * 0.28f),
                    radius = w * 0.22f
                ),
                radius = w * 0.18f,
                center = Offset(w * 0.34f, h * 0.32f)
            )
        }

        fun drawLargeSun() {
            val sunCenter = Offset(w * 0.58f, h * 0.48f)
            val sunRadius = w * 0.25f

            // Large outer glow makes the sun feel warmer and more important in the hero card.
            drawCircle(
                color = Color(0xFFFFD66B).copy(alpha = 0.22f),
                radius = w * 0.42f,
                center = sunCenter
            )

            // Second glow layer adds depth without making the icon look too sharp.
            drawCircle(
                color = Color(0xFFFFE79A).copy(alpha = 0.20f),
                radius = w * 0.33f,
                center = sunCenter
            )

            // Main sun body with a radial gradient so it looks less flat.
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFF7B8),
                        Color(0xFFFFD447),
                        Color(0xFFFFA000)
                    ),
                    center = Offset(
                        sunCenter.x - sunRadius * 0.30f,
                        sunCenter.y - sunRadius * 0.35f
                    ),
                    radius = sunRadius * 1.35f
                ),
                radius = sunRadius,
                center = sunCenter
            )

            // Small highlight gives the sun a polished, slightly dimensional look.
            drawCircle(
                color = Color.White.copy(alpha = 0.36f),
                radius = sunRadius * 0.20f,
                center = Offset(
                    sunCenter.x - sunRadius * 0.35f,
                    sunCenter.y - sunRadius * 0.35f
                )
            )
        }

        fun drawRainDrops() {
            val rainColor = Color(0xFF2DD4FF)

            drawLine(
                color = rainColor,
                start = Offset(w * 0.40f, h * 0.73f),
                end = Offset(w * 0.34f, h * 0.88f),
                strokeWidth = w * 0.06f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = rainColor,
                start = Offset(w * 0.55f, h * 0.75f),
                end = Offset(w * 0.49f, h * 0.90f),
                strokeWidth = w * 0.06f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = rainColor,
                start = Offset(w * 0.70f, h * 0.73f),
                end = Offset(w * 0.64f, h * 0.88f),
                strokeWidth = w * 0.06f,
                cap = StrokeCap.Round
            )
        }

        fun drawLightning() {
            val path = Path().apply {
                moveTo(w * 0.52f, h * 0.58f)
                lineTo(w * 0.42f, h * 0.78f)
                lineTo(w * 0.54f, h * 0.76f)
                lineTo(w * 0.46f, h * 0.96f)
                lineTo(w * 0.68f, h * 0.68f)
                lineTo(w * 0.56f, h * 0.70f)
                close()
            }

            drawPath(
                path = path,
                color = Color(0xFFFFD600)
            )
        }

        fun drawWindLines() {
            val windColor = Color.White.copy(alpha = 0.88f)

            drawLine(
                color = windColor,
                start = Offset(w * 0.18f, h * 0.75f),
                end = Offset(w * 0.70f, h * 0.75f),
                strokeWidth = w * 0.045f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = windColor.copy(alpha = 0.75f),
                start = Offset(w * 0.28f, h * 0.88f),
                end = Offset(w * 0.82f, h * 0.88f),
                strokeWidth = w * 0.04f,
                cap = StrokeCap.Round
            )

            drawArc(
                color = windColor.copy(alpha = 0.75f),
                startAngle = 200f,
                sweepAngle = 230f,
                useCenter = false,
                topLeft = Offset(w * 0.60f, h * 0.68f),
                size = Size(w * 0.22f, h * 0.22f),
                style = Stroke(width = w * 0.035f, cap = StrokeCap.Round)
            )
        }

        fun drawSnow() {
            val snowColor = Color.White.copy(alpha = 0.9f)

            drawLine(
                color = snowColor,
                start = Offset(w * 0.45f, h * 0.78f),
                end = Offset(w * 0.45f, h * 0.94f),
                strokeWidth = w * 0.025f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = snowColor,
                start = Offset(w * 0.38f, h * 0.86f),
                end = Offset(w * 0.52f, h * 0.86f),
                strokeWidth = w * 0.025f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = snowColor,
                start = Offset(w * 0.40f, h * 0.80f),
                end = Offset(w * 0.50f, h * 0.92f),
                strokeWidth = w * 0.025f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = snowColor,
                start = Offset(w * 0.50f, h * 0.80f),
                end = Offset(w * 0.40f, h * 0.92f),
                strokeWidth = w * 0.025f,
                cap = StrokeCap.Round
            )
        }

        when (type) {
            HeroWeatherType.RAIN -> {
                drawSoftCloud()
                drawRainDrops()
            }

            HeroWeatherType.SUN -> {
                drawLargeSun()
            }

            HeroWeatherType.HOT -> {
                drawLargeSun()
            }

            HeroWeatherType.WIND -> {
                drawSoftCloud()
                drawWindLines()
            }

            HeroWeatherType.COOL -> {
                drawSoftCloud()
                drawSnow()
            }

            HeroWeatherType.READY -> {
                drawSun()
                drawSoftCloud()
            }
        }

        if (type == HeroWeatherType.RAIN && weather.rainChance >= 80) {
            drawLightning()
        }
    }
}

