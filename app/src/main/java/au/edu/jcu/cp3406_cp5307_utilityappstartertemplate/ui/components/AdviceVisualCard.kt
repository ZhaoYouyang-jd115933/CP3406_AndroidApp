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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
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
    // Each advice type uses a different gradient to make the visual feedback recognizable.
    val backgroundColors = when (advice.type) {
        AdviceType.UMBRELLA -> listOf(Color(0xFFA7D8FF), Color(0xFF2563EB))
        AdviceType.SUNSCREEN -> listOf(Color(0xFFFFC1DC), Color(0xFFDB2777))
        AdviceType.HYDRATE -> listOf(Color(0xFF8BEFFF), Color(0xFF0891B2))
        AdviceType.LAYER_UP -> listOf(Color(0xFFE4CCFF), Color(0xFF7C3AED))
        AdviceType.WIND_CARE -> listOf(Color(0xFFB7F7D7), Color(0xFF059669))
        AdviceType.READY -> listOf(Color(0xFFFFE9A8), Color(0xFFF59E0B))
    }

    ElevatedCard(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.verticalGradient(backgroundColors))
                .padding(12.dp),
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
                    AdviceType.UMBRELLA -> UmbrellaScene()
                    AdviceType.SUNSCREEN -> SunscreenScene()
                    AdviceType.HYDRATE -> HydrateScene()
                    AdviceType.LAYER_UP -> LayerUpScene()
                    AdviceType.WIND_CARE -> WindCareScene()
                    AdviceType.READY -> ReadyScene()
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
private fun UmbrellaScene() {
    val transition = rememberInfiniteTransition(label = "umbrellaScene")

    val rainOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(950, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rainOffset"
    )

    val umbrellaSwing by transition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "umbrellaSwing"
    )

    val rippleProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rippleProgress"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.52f),
            radius = w * 0.42f,
            color = Color.White
        )

        // Falling diagonal rain lines make the umbrella advice immediately clear.
        for (i in 0..9) {
            val x = w * (0.04f + i * 0.12f)
            val y = ((h * (rainOffset + i * 0.15f)) % h) - h * 0.10f

            drawLine(
                color = Color.White.copy(alpha = 0.55f),
                start = Offset(x, y),
                end = Offset(x - w * 0.055f, y + h * 0.13f),
                strokeWidth = w * 0.030f,
                cap = StrokeCap.Round
            )
        }

        // A subtle ripple suggests rain hitting the ground.
        val rippleRadius = w * (0.12f + rippleProgress * 0.18f)

        drawOval(
            color = Color.White.copy(alpha = 0.28f * (1f - rippleProgress)),
            topLeft = Offset(
                w * 0.50f - rippleRadius,
                h * 0.82f - rippleRadius * 0.25f
            ),
            size = Size(rippleRadius * 2f, rippleRadius * 0.50f),
            style = Stroke(width = w * 0.020f, cap = StrokeCap.Round)
        )

        rotate(
            degrees = umbrellaSwing,
            pivot = Offset(w * 0.50f, h * 0.42f)
        ) {
            drawUmbrella(
                center = Offset(w * 0.50f, h * 0.43f),
                size = w * 0.62f
            )
        }
    }
}

@Composable
private fun SunscreenScene() {
    val transition = rememberInfiniteTransition(label = "sunscreenScene")

    val rayRotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rayRotation"
    )

    val shieldPulse by transition.animateFloat(
        initialValue = 0.78f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shieldPulse"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.48f),
            radius = w * 0.45f,
            color = Color.White
        )

        // Rotating rays make the UV warning feel active without being too distracting.
        drawSunDisc(
            center = Offset(w * 0.50f, h * 0.42f),
            radius = w * 0.20f,
            rayRotation = rayRotation,
            hot = false
        )

        // A shield shape represents sun protection.
        drawShield(
            center = Offset(w * 0.50f, h * 0.58f),
            width = w * 0.52f * shieldPulse,
            height = h * 0.48f * shieldPulse,
            alpha = 0.42f
        )

        drawSmallSparkle(
            center = Offset(w * 0.22f, h * 0.30f),
            radius = w * 0.045f,
            alpha = 0.70f
        )

        drawSmallSparkle(
            center = Offset(w * 0.78f, h * 0.68f),
            radius = w * 0.035f,
            alpha = 0.55f
        )
    }
}

@Composable
private fun HydrateScene() {
    val transition = rememberInfiniteTransition(label = "hydrateScene")

    val bubbleOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleOffset"
    )

    val waveShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveShift"
    )

    val dropFloat by transition.animateFloat(
        initialValue = -3f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dropFloat"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.54f),
            radius = w * 0.42f,
            color = Color.White
        )

        // Soft heat waves hint that hydration is needed because of warm conditions.
        for (i in 0..2) {
            val baseX = w * (0.18f + i * 0.20f)
            val heat = Path().apply {
                moveTo(baseX, h * 0.18f)
                quadraticTo(
                    baseX + w * 0.05f,
                    h * 0.27f,
                    baseX,
                    h * 0.37f
                )
            }

            drawPath(
                path = heat,
                color = Color.White.copy(alpha = 0.22f),
                style = Stroke(width = w * 0.020f, cap = StrokeCap.Round)
            )
        }

        drawWaterDrop(
            center = Offset(w * 0.50f, h * 0.45f + dropFloat.dp.toPx()),
            size = w * 0.38f
        )

        drawWaveLine(
            startX = w * 0.23f,
            y = h * 0.75f,
            width = w * 0.54f,
            shift = waveShift
        )

        // Rising bubbles reinforce the water theme and make the card feel alive.
        for (i in 0..4) {
            val progress = (bubbleOffset + i * 0.22f) % 1f
            val x = w * (0.70f + if (i % 2 == 0) 0.05f else -0.04f)
            val y = h * (0.78f - progress * 0.55f)

            drawCircle(
                color = Color.White.copy(alpha = 0.58f * (1f - progress * 0.35f)),
                radius = w * (0.018f + i * 0.003f),
                center = Offset(x, y)
            )
        }
    }
}

@Composable
private fun LayerUpScene() {
    val transition = rememberInfiniteTransition(label = "layerScene")

    val snowOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "snowOffset"
    )

    val scarfSway by transition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1250),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scarfSway"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.54f),
            radius = w * 0.44f,
            color = Color.White
        )

        // Falling snowflakes make the cold-weather advice immediately recognizable.
        for (i in 0..7) {
            val x = w * (0.10f + i * 0.12f)
            val y = h * (((snowOffset + i * 0.17f) % 1f) * 0.92f)

            drawSnowflake(
                center = Offset(x, y),
                radius = w * 0.024f,
                alpha = 0.65f
            )
        }

        // A hooded winter coat is easier to recognize than an abstract scarf or hat.
        drawWinterLayerIcon(
            center = Offset(w * 0.50f, h * 0.54f),
            width = w * 0.62f,
            height = h * 0.48f,
            sway = scarfSway
        )

        drawArc(
            color = Color.White.copy(alpha = 0.28f),
            startAngle = 205f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(w * 0.18f, h * 0.68f),
            size = Size(w * 0.26f, h * 0.18f),
            style = Stroke(width = w * 0.020f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun WindCareScene() {
    val transition = rememberInfiniteTransition(label = "windScene")

    val windShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1250, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "windShift"
    )

    val leafSpin by transition.animateFloat(
        initialValue = -18f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "leafSpin"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.52f),
            radius = w * 0.44f,
            color = Color.White
        )

        // Several moving curves create a layered wind flow effect.
        for (i in 0..4) {
            val baseY = h * (0.22f + i * 0.13f)
            val startX = w * (((windShift + i * 0.19f) % 1f) - 0.45f)

            val path = Path().apply {
                moveTo(startX, baseY)
                quadraticTo(
                    startX + w * 0.26f,
                    baseY - h * 0.07f,
                    startX + w * 0.52f,
                    baseY
                )
                quadraticTo(
                    startX + w * 0.76f,
                    baseY + h * 0.05f,
                    startX + w * 1.05f,
                    baseY
                )
            }

            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.54f),
                style = Stroke(width = w * 0.027f, cap = StrokeCap.Round)
            )
        }

        // Floating leaves make the wind condition more concrete.
        for (i in 0..2) {
            val progress = (windShift + i * 0.33f) % 1f
            val x = w * (0.10f + progress * 0.82f)
            val y = h * (0.62f + i * 0.08f)

            rotate(
                degrees = leafSpin + i * 18f,
                pivot = Offset(x, y)
            ) {
                drawLeaf(
                    center = Offset(x, y),
                    width = w * 0.12f,
                    height = h * 0.07f,
                    alpha = 0.68f
                )
            }
        }
    }
}

@Composable
private fun ReadyScene() {
    val transition = rememberInfiniteTransition(label = "readyScene")

    val pulse by transition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1150),
            repeatMode = RepeatMode.Reverse
        ),
        label = "readyPulse"
    )

    val sparkle by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(850),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle"
    )

    Canvas(modifier = Modifier.size(108.dp)) {
        val w = size.width
        val h = size.height

        drawGlow(
            center = Offset(w * 0.50f, h * 0.52f),
            radius = w * 0.46f,
            color = Color.White
        )

        drawSunDisc(
            center = Offset(w * 0.22f, h * 0.24f),
            radius = w * 0.08f,
            rayRotation = 0f,
            hot = false
        )

        drawSmallCloud(
            center = Offset(w * 0.75f, h * 0.28f),
            scale = 0.74f
        )

        // A pulsing check mark communicates that conditions are suitable.
        drawCircle(
            color = Color.White.copy(alpha = 0.20f),
            radius = w * 0.27f * pulse,
            center = Offset(w * 0.50f, h * 0.56f)
        )

        drawCircle(
            color = Color.White,
            radius = w * 0.23f * pulse,
            center = Offset(w * 0.50f, h * 0.56f),
            style = Stroke(width = w * 0.040f, cap = StrokeCap.Round)
        )

        drawLine(
            color = Color.White,
            start = Offset(w * 0.39f, h * 0.56f),
            end = Offset(w * 0.47f, h * 0.64f),
            strokeWidth = w * 0.050f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.White,
            start = Offset(w * 0.47f, h * 0.64f),
            end = Offset(w * 0.64f, h * 0.45f),
            strokeWidth = w * 0.050f,
            cap = StrokeCap.Round
        )

        drawSmallSparkle(
            center = Offset(w * 0.82f, h * 0.68f),
            radius = w * 0.055f,
            alpha = sparkle
        )

        drawSmallSparkle(
            center = Offset(w * 0.20f, h * 0.62f),
            radius = w * 0.040f,
            alpha = 1f - sparkle * 0.45f
        )
    }
}

private fun DrawScope.drawUmbrella(
    center: Offset,
    size: Float
) {
    val canopy = Path().apply {
        moveTo(center.x - size * 0.50f, center.y)
        quadraticTo(
            center.x,
            center.y - size * 0.42f,
            center.x + size * 0.50f,
            center.y
        )
        lineTo(center.x - size * 0.50f, center.y)
        close()
    }

    drawPath(
        path = canopy,
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White,
                Color(0xFFE0F2FE)
            )
        )
    )

    drawLine(
        color = Color.White.copy(alpha = 0.95f),
        start = Offset(center.x, center.y),
        end = Offset(center.x, center.y + size * 0.45f),
        strokeWidth = size * 0.055f,
        cap = StrokeCap.Round
    )

    drawArc(
        color = Color.White.copy(alpha = 0.95f),
        startAngle = 15f,
        sweepAngle = 220f,
        useCenter = false,
        topLeft = Offset(center.x - size * 0.02f, center.y + size * 0.35f),
        size = Size(size * 0.25f, size * 0.25f),
        style = Stroke(width = size * 0.045f, cap = StrokeCap.Round)
    )

    for (i in -1..1) {
        drawLine(
            color = Color(0xFF93C5FD).copy(alpha = 0.65f),
            start = center,
            end = Offset(center.x + i * size * 0.23f, center.y - size * 0.01f),
            strokeWidth = size * 0.018f,
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawShield(
    center: Offset,
    width: Float,
    height: Float,
    alpha: Float
) {
    val path = Path().apply {
        moveTo(center.x, center.y - height * 0.48f)
        quadraticTo(
            center.x + width * 0.42f,
            center.y - height * 0.33f,
            center.x + width * 0.34f,
            center.y + height * 0.08f
        )
        quadraticTo(
            center.x + width * 0.24f,
            center.y + height * 0.35f,
            center.x,
            center.y + height * 0.48f
        )
        quadraticTo(
            center.x - width * 0.24f,
            center.y + height * 0.35f,
            center.x - width * 0.34f,
            center.y + height * 0.08f
        )
        quadraticTo(
            center.x - width * 0.42f,
            center.y - height * 0.33f,
            center.x,
            center.y - height * 0.48f
        )
        close()
    }

    drawPath(
        path = path,
        color = Color.White.copy(alpha = alpha),
        style = Stroke(width = width * 0.055f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawWaterDrop(
    center: Offset,
    size: Float
) {
    val path = Path().apply {
        moveTo(center.x, center.y - size * 0.55f)
        cubicTo(
            center.x + size * 0.45f,
            center.y - size * 0.10f,
            center.x + size * 0.34f,
            center.y + size * 0.40f,
            center.x,
            center.y + size * 0.50f
        )
        cubicTo(
            center.x - size * 0.34f,
            center.y + size * 0.40f,
            center.x - size * 0.45f,
            center.y - size * 0.10f,
            center.x,
            center.y - size * 0.55f
        )
        close()
    }

    drawPath(
        path = path,
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White,
                Color.White.copy(alpha = 0.52f)
            ),
            center = Offset(
                center.x - size * 0.18f,
                center.y - size * 0.18f
            ),
            radius = size * 0.68f
        )
    )

    drawCircle(
        color = Color.White.copy(alpha = 0.40f),
        radius = size * 0.09f,
        center = Offset(
            center.x - size * 0.14f,
            center.y - size * 0.20f
        )
    )
}

private fun DrawScope.drawWinterLayerIcon(
    center: Offset,
    width: Float,
    height: Float,
    sway: Float
) {
    // A hooded winter coat is more recognizable.
    val coatWidth = width * 0.62f
    val coatHeight = height * 0.78f
    val coatTop = center.y - coatHeight * 0.38f
    val coatBottom = coatTop + coatHeight

    // Soft shadow under the coat.
    drawOval(
        color = Color.White.copy(alpha = 0.18f),
        topLeft = Offset(
            center.x - width * 0.36f,
            coatBottom - height * 0.02f
        ),
        size = Size(width * 0.72f, height * 0.16f)
    )

    // Left sleeve.
    rotate(
        degrees = 12f,
        pivot = Offset(center.x - coatWidth * 0.42f, coatTop + coatHeight * 0.34f)
    ) {
        drawRoundRect(
            color = Color.White.copy(alpha = 0.70f),
            topLeft = Offset(
                center.x - coatWidth * 0.68f,
                coatTop + coatHeight * 0.22f
            ),
            size = Size(coatWidth * 0.24f, coatHeight * 0.58f),
            cornerRadius = CornerRadius(width * 0.08f, width * 0.08f)
        )
    }

    // Right sleeve.
    rotate(
        degrees = -12f,
        pivot = Offset(center.x + coatWidth * 0.42f, coatTop + coatHeight * 0.34f)
    ) {
        drawRoundRect(
            color = Color.White.copy(alpha = 0.70f),
            topLeft = Offset(
                center.x + coatWidth * 0.44f,
                coatTop + coatHeight * 0.22f
            ),
            size = Size(coatWidth * 0.24f, coatHeight * 0.58f),
            cornerRadius = CornerRadius(width * 0.08f, width * 0.08f)
        )
    }

    // Main winter coat body.
    val coatPath = Path().apply {
        moveTo(center.x - coatWidth * 0.36f, coatTop + coatHeight * 0.18f)
        quadraticTo(
            center.x,
            coatTop - coatHeight * 0.02f,
            center.x + coatWidth * 0.36f,
            coatTop + coatHeight * 0.18f
        )
        lineTo(center.x + coatWidth * 0.48f, coatBottom)
        lineTo(center.x - coatWidth * 0.48f, coatBottom)
        close()
    }

    drawPath(
        path = coatPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White,
                Color(0xFFF1ECFF)
            )
        )
    )

    // Hood opening.
    drawArc(
        color = Color(0xFFA78BFA).copy(alpha = 0.45f),
        startAngle = 200f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(
            center.x - coatWidth * 0.24f,
            coatTop + coatHeight * 0.04f
        ),
        size = Size(coatWidth * 0.48f, coatHeight * 0.34f),
        style = Stroke(
            width = width * 0.035f,
            cap = StrokeCap.Round
        )
    )

    // Scarf around the neck.
    drawRoundRect(
        color = Color(0xFFFFE4F1),
        topLeft = Offset(
            center.x - coatWidth * 0.42f,
            coatTop + coatHeight * 0.28f
        ),
        size = Size(coatWidth * 0.84f, coatHeight * 0.14f),
        cornerRadius = CornerRadius(width * 0.08f, width * 0.08f)
    )

    // Animated scarf tail gives the icon a small but visible movement.
    rotate(
        degrees = sway,
        pivot = Offset(
            center.x + coatWidth * 0.26f,
            coatTop + coatHeight * 0.36f
        )
    ) {
        drawRoundRect(
            color = Color(0xFFFFE4F1).copy(alpha = 0.92f),
            topLeft = Offset(
                center.x + coatWidth * 0.22f,
                coatTop + coatHeight * 0.38f
            ),
            size = Size(coatWidth * 0.22f, coatHeight * 0.42f),
            cornerRadius = CornerRadius(width * 0.06f, width * 0.06f)
        )

        drawLine(
            color = Color(0xFFA78BFA).copy(alpha = 0.45f),
            start = Offset(
                center.x + coatWidth * 0.26f,
                coatTop + coatHeight * 0.60f
            ),
            end = Offset(
                center.x + coatWidth * 0.40f,
                coatTop + coatHeight * 0.60f
            ),
            strokeWidth = width * 0.018f,
            cap = StrokeCap.Round
        )
    }

    // Zipper line.
    drawLine(
        color = Color(0xFFA78BFA).copy(alpha = 0.55f),
        start = Offset(center.x, coatTop + coatHeight * 0.42f),
        end = Offset(center.x, coatBottom - coatHeight * 0.12f),
        strokeWidth = width * 0.030f,
        cap = StrokeCap.Round
    )

    // Pockets make it read clearly as a coat.
    drawRoundRect(
        color = Color(0xFFA78BFA).copy(alpha = 0.32f),
        topLeft = Offset(
            center.x - coatWidth * 0.34f,
            coatTop + coatHeight * 0.62f
        ),
        size = Size(coatWidth * 0.22f, coatHeight * 0.12f),
        cornerRadius = CornerRadius(width * 0.04f, width * 0.04f)
    )

    drawRoundRect(
        color = Color(0xFFA78BFA).copy(alpha = 0.32f),
        topLeft = Offset(
            center.x + coatWidth * 0.12f,
            coatTop + coatHeight * 0.62f
        ),
        size = Size(coatWidth * 0.22f, coatHeight * 0.12f),
        cornerRadius = CornerRadius(width * 0.04f, width * 0.04f)
    )

    // Buttons add detail without making the icon too busy.
    for (i in 0..2) {
        drawCircle(
            color = Color(0xFFA78BFA).copy(alpha = 0.55f),
            radius = width * 0.022f,
            center = Offset(
                center.x + coatWidth * 0.10f,
                coatTop + coatHeight * (0.48f + i * 0.12f)
            )
        )
    }
}

private fun DrawScope.drawLeaf(
    center: Offset,
    width: Float,
    height: Float,
    alpha: Float
) {
    val leaf = Path().apply {
        moveTo(center.x - width * 0.50f, center.y)
        quadraticTo(
            center.x,
            center.y - height * 0.65f,
            center.x + width * 0.50f,
            center.y
        )
        quadraticTo(
            center.x,
            center.y + height * 0.65f,
            center.x - width * 0.50f,
            center.y
        )
        close()
    }

    drawPath(
        path = leaf,
        color = Color.White.copy(alpha = alpha)
    )

    drawLine(
        color = Color.White.copy(alpha = alpha * 0.75f),
        start = Offset(center.x - width * 0.35f, center.y),
        end = Offset(center.x + width * 0.35f, center.y),
        strokeWidth = width * 0.08f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawWaveLine(
    startX: Float,
    y: Float,
    width: Float,
    shift: Float
) {
    val path = Path().apply {
        moveTo(startX, y)

        for (i in 0..5) {
            val x1 = startX + width * (i / 5f)
            val x2 = startX + width * ((i + 1) / 5f)
            val controlY = y + if ((i + (shift * 10).toInt()) % 2 == 0) -10f else 10f

            quadraticTo(
                (x1 + x2) / 2f,
                controlY,
                x2,
                y
            )
        }
    }

    drawPath(
        path = path,
        color = Color.White.copy(alpha = 0.58f),
        style = Stroke(width = width * 0.050f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawSunDisc(
    center: Offset,
    radius: Float,
    rayRotation: Float,
    hot: Boolean
) {
    val rayColor = if (hot) Color(0xFFFFF1A3) else Color.White

    for (i in 0 until 10) {
        val angle = rayRotation + i * 36f
        val rad = angle.toDouble() * PI / 180.0
        val dx = cos(rad).toFloat()
        val dy = sin(rad).toFloat()

        drawLine(
            color = rayColor.copy(alpha = 0.72f),
            start = Offset(
                center.x + dx * radius * 1.28f,
                center.y + dy * radius * 1.28f
            ),
            end = Offset(
                center.x + dx * radius * 1.75f,
                center.y + dy * radius * 1.75f
            ),
            strokeWidth = radius * 0.13f,
            cap = StrokeCap.Round
        )
    }

    drawCircle(
        color = Color.White.copy(alpha = 0.22f),
        radius = radius * 1.55f,
        center = center
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFFFFF8B0),
                Color(0xFFFFD447),
                Color(0xFFFFA500)
            ),
            center = Offset(
                center.x - radius * 0.25f,
                center.y - radius * 0.35f
            ),
            radius = radius * 1.45f
        ),
        radius = radius,
        center = center
    )

    drawCircle(
        color = Color.White.copy(alpha = 0.38f),
        radius = radius * 0.22f,
        center = Offset(
            center.x - radius * 0.34f,
            center.y - radius * 0.36f
        )
    )
}

private fun DrawScope.drawSmallCloud(
    center: Offset,
    scale: Float
) {
    val u = size.minDimension / 100f * scale

    drawOval(
        color = Color.White.copy(alpha = 0.18f),
        topLeft = Offset(center.x - 23f * u, center.y + 10f * u),
        size = Size(46f * u, 12f * u)
    )

    drawRoundRect(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White,
                Color(0xFFEAF3FF)
            )
        ),
        topLeft = Offset(center.x - 26f * u, center.y - 1f * u),
        size = Size(52f * u, 19f * u),
        cornerRadius = CornerRadius(10f * u, 10f * u)
    )

    drawCircle(
        color = Color.White,
        radius = 13f * u,
        center = Offset(center.x - 12f * u, center.y)
    )

    drawCircle(
        color = Color.White,
        radius = 16f * u,
        center = Offset(center.x + 5f * u, center.y - 4f * u)
    )

    drawCircle(
        color = Color.White.copy(alpha = 0.94f),
        radius = 12f * u,
        center = Offset(center.x + 18f * u, center.y + 2f * u)
    )
}

private fun DrawScope.drawSnowflake(
    center: Offset,
    radius: Float,
    alpha: Float
) {
    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = radius * 0.32f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = radius * 0.32f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(
            center.x - radius * 0.70f,
            center.y - radius * 0.70f
        ),
        end = Offset(
            center.x + radius * 0.70f,
            center.y + radius * 0.70f
        ),
        strokeWidth = radius * 0.25f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(
            center.x + radius * 0.70f,
            center.y - radius * 0.70f
        ),
        end = Offset(
            center.x - radius * 0.70f,
            center.y + radius * 0.70f
        ),
        strokeWidth = radius * 0.25f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawSmallSparkle(
    center: Offset,
    radius: Float,
    alpha: Float
) {
    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = radius * 0.30f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color.White.copy(alpha = alpha),
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = radius * 0.30f,
        cap = StrokeCap.Round
    )

    drawCircle(
        color = Color.White.copy(alpha = alpha * 0.30f),
        radius = radius * 1.35f,
        center = center
    )
}

private fun DrawScope.drawGlow(
    center: Offset,
    radius: Float,
    color: Color
) {
    drawCircle(
        color = color.copy(alpha = 0.12f),
        radius = radius,
        center = center
    )

    drawCircle(
        color = color.copy(alpha = 0.08f),
        radius = radius * 0.72f,
        center = center
    )
}

