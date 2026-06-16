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
    Column(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                brush = Brush.verticalGradient(cardGradient)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
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
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.85f),
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
        val safeProgress = progress.coerceIn(0f, 1f)

        val strokeWidth = 8.dp.toPx()
        val center = Offset(
            x = size.width / 2f,
            y = size.height - 4.dp.toPx()
        )

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

        val angleDegrees = 180f + 180f * safeProgress
        val angleRadians = angleDegrees * PI.toFloat() / 180f

        val needleLength = radius * 0.72f

        val needleEnd = Offset(
            x = center.x + cos(angleRadians) * needleLength,
            y = center.y + sin(angleRadians) * needleLength
        )

        drawLine(
            color = Color.White,
            start = center,
            end = needleEnd,
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = center
        )
    }
}

