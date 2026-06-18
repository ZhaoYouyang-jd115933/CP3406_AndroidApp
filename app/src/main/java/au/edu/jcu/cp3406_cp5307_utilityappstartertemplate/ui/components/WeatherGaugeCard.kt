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
import androidx.compose.ui.graphics.Shadow
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
    // A soft text shadow improves the contrast of white text on light pastel backgrounds.
    // This keeps the visual design bright while making the title and value easier to read.
    val mainTextShadow = Shadow(
        color = Color.Black.copy(alpha = 0.24f),
        offset = Offset(0f, 1.5f),
        blurRadius = 3f
    )

    // The label uses a slightly softer shadow because it is secondary information.
    // This avoids making the small text look too heavy while still improving readability.
    val secondaryTextShadow = Shadow(
        color = Color.Black.copy(alpha = 0.20f),
        offset = Offset(0f, 1.2f),
        blurRadius = 2.5f
    )

    Column(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                // The text styling is adjusted to improve readability.
                brush = Brush.verticalGradient(cardGradient)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                // The shadow helps the white title stand out from pale card colors.
                shadow = mainTextShadow
            ),
            color = Color.White,
            // Bold weight makes short labels such as Rain, UV and Wind clearer.
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
            style = MaterialTheme.typography.titleMedium.copy(
                // The current value is the most important text, so it uses the stronger shadow.
                shadow = mainTextShadow
            ),
            color = Color.White,
            // ExtraBold gives the metric value stronger visual priority.
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(
                // The secondary label still needs contrast, but not overpower the value.
                shadow = secondaryTextShadow
            ),
            // Full white is used instead of translucent white so the label remains readable.
            color = Color.White,
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
        // Clamp the progress value so the needle always stays within the semicircle.
        val safeProgress = progress.coerceIn(0f, 1f)

        val strokeWidth = 8.dp.toPx()

        // The gauge center is placed near the bottom so only the upper semicircle is visible.
        val center = Offset(
            x = size.width / 2f,
            y = size.height - 4.dp.toPx()
        )

        // The radius is calculated from the available width and height to prevent clipping.
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

        // Draw the main semicircle arc using the gradient passed in from the parent screen.
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

        // Convert progress into an angle between 180 and 360 degrees.
        val angleDegrees = 180f + 180f * safeProgress
        val angleRadians = angleDegrees * PI.toFloat() / 180f

        val needleLength = radius * 0.72f

        // Calculate the needle endpoint using basic trigonometry.
        val needleEnd = Offset(
            x = center.x + cos(angleRadians) * needleLength,
            y = center.y + sin(angleRadians) * needleLength
        )

        // Draw the white gauge needle.
        drawLine(
            color = Color.White,
            start = center,
            end = needleEnd,
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Draw the center point of the gauge needle.
        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = center
        )
    }
}

