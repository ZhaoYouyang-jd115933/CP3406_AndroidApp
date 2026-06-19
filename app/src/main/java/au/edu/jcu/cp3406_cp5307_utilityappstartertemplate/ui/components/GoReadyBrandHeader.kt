package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GoReadyBrandHeader(
    modifier: Modifier = Modifier,
    subtitle: String = "Quick daily go-out advice based on weather conditions."
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PremiumWeatherLogo()

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            // A lighter badge keeps the brand label visible without drawing too much attention.
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFF0F6FF),
                                    Color(0xFFF7F3FF)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(999.dp)
                        )
                        .padding(horizontal = 9.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "SMART WEATHER",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF7897CF),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        // "Go" stays dark so the brand name remains readable and grounded.
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF242638),
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append("Go")
                        }

                        // "Ready" uses a soft blue-lilac gradient for a subtle wordmark effect.
                        withStyle(
                            style = SpanStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF6EA8F7),
                                        Color(0xFF72B7FF),
                                        Color(0xFF8C8DFF)
                                    )
                                ),
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append("Ready")
                        }
                    },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        // Slightly larger text gives the app name stronger brand presence.
                        fontSize = 25.sp,
                        letterSpacing = (-0.45).sp,

                        // A very soft shadow makes the wordmark feel more polished without looking flashy.
                        shadow = Shadow(
                            color = Color(0xFF7FA9FF).copy(alpha = 0.16f),
                            offset = Offset(0f, 1.4f),
                            blurRadius = 4f
                        )
                    ),
                    maxLines = 1
                )
            }
        }

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF505566)
        )
    }
}

@Composable
private fun PremiumWeatherLogo(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(54.dp)
            .shadow(
                // A gentle shadow lifts the logo slightly and makes it feel less flat.
                elevation = 8.dp,
                shape = RoundedCornerShape(19.dp)
            )
            .clip(RoundedCornerShape(19.dp))
            .background(
                // The blue gradient visually connects the logo with the rest of the weather UI.
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F6FF),
                        Color(0xFFAED8FF),
                        Color(0xFF78B2F7)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.75f),
                shape = RoundedCornerShape(19.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Soft inner glow gives the logo a cleaner and more polished appearance.
            drawCircle(
                color = Color.White.copy(alpha = 0.28f),
                radius = w * 0.55f,
                center = Offset(w * 0.42f, h * 0.36f)
            )

            // A subtle shadow underneath helps separate the weather symbol from the background.
            drawOval(
                color = Color(0xFF356FAF).copy(alpha = 0.16f),
                topLeft = Offset(w * 0.22f, h * 0.66f),
                size = Size(w * 0.55f, h * 0.16f)
            )

            // Sun glow behind the cloud.
            drawCircle(
                color = Color(0xFFFFD864).copy(alpha = 0.30f),
                radius = w * 0.26f,
                center = Offset(w * 0.34f, h * 0.34f)
            )

            // Main sun body.
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFF6B8),
                        Color(0xFFFFCA3A)
                    ),
                    center = Offset(w * 0.30f, h * 0.29f),
                    radius = w * 0.26f
                ),
                radius = w * 0.16f,
                center = Offset(w * 0.34f, h * 0.35f)
            )

            // Short sun rays make the icon more recognizable at small size.
            for (i in 0 until 8) {
                val angle = Math.toRadians((i * 45).toDouble())

                val start = Offset(
                    x = w * 0.34f + kotlin.math.cos(angle).toFloat() * w * 0.22f,
                    y = h * 0.35f + kotlin.math.sin(angle).toFloat() * w * 0.22f
                )

                val end = Offset(
                    x = w * 0.34f + kotlin.math.cos(angle).toFloat() * w * 0.29f,
                    y = h * 0.35f + kotlin.math.sin(angle).toFloat() * w * 0.29f
                )

                drawLine(
                    color = Color.White.copy(alpha = 0.82f),
                    start = start,
                    end = end,
                    strokeWidth = w * 0.035f,
                    cap = StrokeCap.Round
                )
            }

            // Cloud base.
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFEAF3FF)
                    )
                ),
                topLeft = Offset(w * 0.22f, h * 0.48f),
                size = Size(w * 0.55f, h * 0.23f),
                cornerRadius = CornerRadius(w * 0.13f, w * 0.13f)
            )

            // Cloud puffs create a softer and friendlier weather symbol.
            drawCircle(
                color = Color.White,
                radius = w * 0.14f,
                center = Offset(w * 0.36f, h * 0.48f)
            )

            drawCircle(
                color = Color.White,
                radius = w * 0.19f,
                center = Offset(w * 0.52f, h * 0.43f)
            )

            drawCircle(
                color = Color.White.copy(alpha = 0.96f),
                radius = w * 0.14f,
                center = Offset(w * 0.68f, h * 0.51f)
            )

            // Blue check badge reinforces the app's "ready to go" meaning.
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF9FC2FF),
                        Color(0xFF5F8FEF)
                    ),
                    center = Offset(w * 0.70f, h * 0.68f),
                    radius = w * 0.23f
                ),
                radius = w * 0.16f,
                center = Offset(w * 0.74f, h * 0.73f)
            )

            // Small highlight makes the badge look more polished.
            drawCircle(
                color = Color.White.copy(alpha = 0.22f),
                radius = w * 0.055f,
                center = Offset(w * 0.69f, h * 0.68f)
            )

            drawLine(
                color = Color.White,
                start = Offset(w * 0.67f, h * 0.73f),
                end = Offset(w * 0.72f, h * 0.78f),
                strokeWidth = w * 0.04f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color.White,
                start = Offset(w * 0.72f, h * 0.78f),
                end = Offset(w * 0.82f, h * 0.66f),
                strokeWidth = w * 0.04f,
                cap = StrokeCap.Round
            )

            // Curved highlight gives the lower-right badge a more refined finish.
            drawArc(
                color = Color.White.copy(alpha = 0.38f),
                startAngle = 210f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(w * 0.62f, h * 0.61f),
                size = Size(w * 0.22f, h * 0.22f),
                style = Stroke(
                    width = w * 0.018f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}