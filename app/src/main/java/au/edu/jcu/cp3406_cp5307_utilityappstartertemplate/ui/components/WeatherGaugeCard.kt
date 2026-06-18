package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun WeatherGaugeCard(
    title: String,
    valueText: String,
    label: String,
    progress: Float,
    cardGradient: List<Color>,
    arcGradient: List<Color>,
    modifier: Modifier = Modifier
) {
    // A muted dark lavender-gray is used for the main text.
    // It stays readable on light pastel backgrounds while still feeling soft and cohesive.
    val primaryTextColor = Color(0xFF5B5873)

    // A slightly lighter companion color is used for secondary text.
    // This keeps the visual hierarchy clear without making the card look too heavy.
    val secondaryTextColor = Color(0xFF78758F)

    Column(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                // Keep the original card background unchanged.
                // Only the text styling is adjusted to improve readability and visual balance.
                brush = Brush.verticalGradient(cardGradient)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = primaryTextColor,
            // Bold weight helps short headings such as Rain, UV, and Wind stand out clearly.
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        SemiCircleGauge(
            progress = progress,
            arcGradient = arcGradient,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        )

        Text(
            text = valueText,
            style = MaterialTheme.typography.titleLarge,
            color = primaryTextColor,
            // ExtraBold gives the metric value the strongest visual emphasis in the card.
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = secondaryTextColor,
            // SemiBold keeps the supporting label readable without competing with the main value.
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun SemiCircleGauge(
    progress: Float,
    arcGradient: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        // Clamp the progress so the needle always stays within the valid semicircle range.
        val safeProgress = progress.coerceIn(0f, 1f)

        val strokeWidth = 8.dp.toPx()

        // The center is placed near the bottom so only the upper half of the gauge is shown.
        val center = Offset(
            x = size.width / 2f,
            y = size.height - 4.dp.toPx()
        )

        // Calculate the largest radius that fits within the available canvas space.
        val radius = min(
            size.width / 2f - strokeWidth,
            size.height - strokeWidth
        )

        val topLeft = Offset(
            x = center.x - radius,
            y = center.y - radius
        )

        val arcSize = Size(
            width = radius * 2f,
            height = radius * 2f
        )

        // Draw the main arc using the gradient provided by the parent composable.
        drawArc(
            brush = Brush.linearGradient(
                colors = arcGradient,
                start = Offset(center.x - radius, center.y),
                end = Offset(center.x + radius, center.y)
            ),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        // Convert the progress value into the angle used to position the needle.
        val angleDegrees = 180f + 180f * safeProgress
        val angleRadians = angleDegrees * PI.toFloat() / 180f

        val needleLength = radius * 0.72f

        // Use trigonometry to calculate the needle endpoint.
        val needleEnd = Offset(
            x = center.x + cos(angleRadians) * needleLength,
            y = center.y + sin(angleRadians) * needleLength
        )

        // Keep the gauge needle white so it remains visually distinct from the darker text.
        drawLine(
            color = Color.White,
            start = center,
            end = needleEnd,
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Draw the center dot of the gauge needle.
        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = center
        )
    }
}

