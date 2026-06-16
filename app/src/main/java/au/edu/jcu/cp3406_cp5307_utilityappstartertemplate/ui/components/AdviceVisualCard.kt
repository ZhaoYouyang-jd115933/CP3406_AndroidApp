package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceType
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceUiModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AdviceVisualCard(
    advice: AdviceUiModel,
    modifier: Modifier = Modifier
) {
    val backgroundColors = when (advice.type) {
        AdviceType.UMBRELLA -> listOf(Color(0xFF93C5FD), Color(0xFF2563EB))
        AdviceType.SUNSCREEN -> listOf(Color(0xFFF9A8D4), Color(0xFFDB2777))
        AdviceType.HYDRATE -> listOf(Color(0xFF67E8F9), Color(0xFF0891B2))
        AdviceType.LAYER_UP -> listOf(Color(0xFFD8B4FE), Color(0xFF7C3AED))
        AdviceType.WIND_CARE -> listOf(Color(0xFFA7F3D0), Color(0xFF059669))
        AdviceType.READY -> listOf(Color(0xFFFDE68A), Color(0xFFF59E0B))
    }

    ElevatedCard(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(backgroundColors))
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (advice.type) {
                    AdviceType.UMBRELLA -> UmbrellaAnimation()
                    AdviceType.SUNSCREEN -> SunscreenAnimation()
                    AdviceType.HYDRATE -> HydrateAnimation()
                    AdviceType.LAYER_UP -> LayerUpAnimation()
                    AdviceType.WIND_CARE -> WindCareAnimation()
                    AdviceType.READY -> ReadyAnimation()
                }
            }

            Text(
                text = advice.visualLabel,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ReadyAnimation() {
    val transition = rememberInfiniteTransition(label = "ready")
    val pulse by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "readyPulse"
    )

    Canvas(
        modifier = Modifier
            .size(84.dp)
            .graphicsLayer(scaleX = pulse, scaleY = pulse)
    ) {
        val r = size.minDimension / 2.4f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        drawCircle(
            color = Color.White.copy(alpha = 0.16f),
            radius = r + 10.dp.toPx()
        )

        drawCircle(
            color = Color.White,
            radius = r,
            style = Stroke(width = 5.dp.toPx())
        )

        drawLine(
            color = Color.White,
            start = androidx.compose.ui.geometry.Offset(centerX - 12.dp.toPx(), centerY + 2.dp.toPx()),
            end = androidx.compose.ui.geometry.Offset(centerX - 2.dp.toPx(), centerY + 12.dp.toPx()),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.White,
            start = androidx.compose.ui.geometry.Offset(centerX - 2.dp.toPx(), centerY + 12.dp.toPx()),
            end = androidx.compose.ui.geometry.Offset(centerX + 16.dp.toPx(), centerY - 10.dp.toPx()),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun UmbrellaAnimation() {
    val transition = rememberInfiniteTransition(label = "umbrella")
    val rainShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rainShift"
    )

    Canvas(modifier = Modifier.size(88.dp)) {
        val cx = size.width / 2f
        val cy = size.height / 2f + 4.dp.toPx()

        for (xOffset in listOf(-18.dp.toPx(), 0f, 18.dp.toPx())) {
            drawLine(
                color = Color.White.copy(alpha = 0.55f),
                start = androidx.compose.ui.geometry.Offset(cx + xOffset, cy - 34.dp.toPx() + rainShift),
                end = androidx.compose.ui.geometry.Offset(cx + xOffset, cy - 24.dp.toPx() + rainShift),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        val canopy = Path().apply {
            moveTo(cx - 28.dp.toPx(), cy)
            quadraticTo(cx, cy - 24.dp.toPx(), cx + 28.dp.toPx(), cy)
            lineTo(cx - 28.dp.toPx(), cy)
            close()
        }

        drawPath(
            path = canopy,
            color = Color.White.copy(alpha = 0.95f)
        )

        drawLine(
            color = Color.White,
            start = androidx.compose.ui.geometry.Offset(cx, cy),
            end = androidx.compose.ui.geometry.Offset(cx, cy + 28.dp.toPx()),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        val hook = Path().apply {
            moveTo(cx, cy + 28.dp.toPx())
            quadraticTo(
                cx + 2.dp.toPx(),
                cy + 40.dp.toPx(),
                cx + 12.dp.toPx(),
                cy + 36.dp.toPx()
            )
        }

        drawPath(
            path = hook,
            color = Color.White,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun SunscreenAnimation() {
    val transition = rememberInfiniteTransition(label = "sunscreen")
    val pulse by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sunPulse"
    )

    Canvas(modifier = Modifier.size(88.dp)) {
        val cx = size.width / 2f
        val sunCenter = androidx.compose.ui.geometry.Offset(cx + 18.dp.toPx(), 18.dp.toPx())

        drawCircle(
            color = Color.White.copy(alpha = 0.2f),
            radius = 12.dp.toPx() * pulse,
            center = sunCenter
        )

        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = sunCenter
        )

        for (i in 0 until 8) {
            val angle = i * 45f
            val rad = angle * PI.toFloat() / 180f

            val start = androidx.compose.ui.geometry.Offset(
                sunCenter.x + cos(rad) * 12.dp.toPx(),
                sunCenter.y + sin(rad) * 12.dp.toPx()
            )

            val end = androidx.compose.ui.geometry.Offset(
                sunCenter.x + cos(rad) * 18.dp.toPx() * pulse,
                sunCenter.y + sin(rad) * 18.dp.toPx() * pulse
            )

            drawLine(
                color = Color.White,
                start = start,
                end = end,
                strokeWidth = 2.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        drawRoundRect(
            color = Color.White.copy(alpha = 0.95f),
            topLeft = androidx.compose.ui.geometry.Offset(cx - 14.dp.toPx(), 34.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(28.dp.toPx(), 34.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
        )

        drawRoundRect(
            color = Color.White.copy(alpha = 0.95f),
            topLeft = androidx.compose.ui.geometry.Offset(cx - 6.dp.toPx(), 26.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(12.dp.toPx(), 10.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
        )

        drawLine(
            color = Color(0xFFF9A8D4),
            start = androidx.compose.ui.geometry.Offset(cx - 7.dp.toPx(), 50.dp.toPx()),
            end = androidx.compose.ui.geometry.Offset(cx + 7.dp.toPx(), 50.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun HydrateAnimation() {
    val transition = rememberInfiniteTransition(label = "hydrate")
    val waveShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveShift"
    )

    Canvas(modifier = Modifier.size(88.dp)) {
        val left = size.width / 2f - 14.dp.toPx()
        val top = 18.dp.toPx()
        val width = 28.dp.toPx()
        val height = 48.dp.toPx()

        drawRoundRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(left, top),
            size = androidx.compose.ui.geometry.Size(width, height),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(10.dp.toPx()),
            style = Stroke(width = 4.dp.toPx())
        )

        drawRoundRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(size.width / 2f - 7.dp.toPx(), 10.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(14.dp.toPx(), 10.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
        )

        val waterTop = top + 26.dp.toPx()
        val wave = Path().apply {
            moveTo(left + 3.dp.toPx(), top + height - 4.dp.toPx())
            lineTo(left + 3.dp.toPx(), waterTop)

            for (x in 0..4) {
                val startX = left + 3.dp.toPx() + x * 6.dp.toPx()
                quadraticTo(
                    startX + 3.dp.toPx(),
                    waterTop + if (x % 2 == 0) -3.dp.toPx() else 3.dp.toPx(),
                    startX + 6.dp.toPx(),
                    waterTop
                )
            }

            lineTo(left + width - 3.dp.toPx(), top + height - 4.dp.toPx())
            close()
        }

        translate(left = waveShift % 12f - 6f) {
            drawPath(
                path = wave,
                color = Color.White.copy(alpha = 0.35f)
            )
        }

        drawCircle(
            color = Color.White.copy(alpha = 0.7f),
            radius = 4.dp.toPx(),
            center = androidx.compose.ui.geometry.Offset(size.width / 2f + 22.dp.toPx(), 24.dp.toPx())
        )
    }
}

@Composable
private fun LayerUpAnimation() {
    val transition = rememberInfiniteTransition(label = "layer")
    val sway by transition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "layerSway"
    )

    Canvas(
        modifier = Modifier
            .size(88.dp)
            .graphicsLayer(rotationZ = sway)
    ) {
        val cx = size.width / 2f
        val cy = size.height / 2f

        drawRoundRect(
            color = Color.White.copy(alpha = 0.95f),
            topLeft = androidx.compose.ui.geometry.Offset(cx - 18.dp.toPx(), cy - 18.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(36.dp.toPx(), 16.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
        )

        drawRoundRect(
            color = Color.White.copy(alpha = 0.95f),
            topLeft = androidx.compose.ui.geometry.Offset(cx - 10.dp.toPx(), cy - 4.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(10.dp.toPx(), 34.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())
        )

        drawRoundRect(
            color = Color.White.copy(alpha = 0.78f),
            topLeft = androidx.compose.ui.geometry.Offset(cx + 2.dp.toPx(), cy - 2.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(10.dp.toPx(), 24.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx())
        )
    }
}

@Composable
private fun WindCareAnimation() {
    val transition = rememberInfiniteTransition(label = "wind")
    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "windShift"
    )

    Canvas(modifier = Modifier.size(88.dp)) {
        val lineColor = Color.White.copy(alpha = 0.8f)

        for (i in 0..2) {
            val y = 24.dp.toPx() + i * 12.dp.toPx()
            val path = Path().apply {
                moveTo(14.dp.toPx() - shift, y)
                quadraticTo(
                    34.dp.toPx() - shift,
                    y - 6.dp.toPx(),
                    54.dp.toPx() - shift,
                    y
                )
                quadraticTo(
                    68.dp.toPx() - shift,
                    y + 5.dp.toPx(),
                    78.dp.toPx() - shift,
                    y
                )
            }

            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        rotate(degrees = 18f, pivot = androidx.compose.ui.geometry.Offset(60.dp.toPx(), 50.dp.toPx())) {
            drawOval(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = androidx.compose.ui.geometry.Offset(52.dp.toPx(), 44.dp.toPx()),
                size = androidx.compose.ui.geometry.Size(18.dp.toPx(), 10.dp.toPx())
            )

            drawLine(
                color = Color.White,
                start = androidx.compose.ui.geometry.Offset(52.dp.toPx(), 49.dp.toPx()),
                end = androidx.compose.ui.geometry.Offset(46.dp.toPx(), 54.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

