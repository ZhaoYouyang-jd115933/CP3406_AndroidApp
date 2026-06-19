package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceType
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceUiModel

@Composable
fun AdviceTextCard(
    advice: AdviceUiModel,
    showDetail: Boolean,
    modifier: Modifier = Modifier,
    expanded: Boolean = false
) {
    // Each advice type uses a different accent color so users can quickly
    // recognize the kind of weather advice being shown.
    val accentColor = when (advice.type) {
        AdviceType.UMBRELLA -> Color(0xFF4F8DFD)
        AdviceType.SUNSCREEN -> Color(0xFFEC4899)
        AdviceType.HYDRATE -> Color(0xFF06B6D4)
        AdviceType.LAYER_UP -> Color(0xFF8B5CF6)
        AdviceType.WIND_CARE -> Color(0xFF10B981)
        AdviceType.READY -> Color(0xFFF59E0B)
    }

    // The top background color is matched with the advice type to create
    // a soft visual connection between the text card and the current weather status.
    val backgroundTop = when (advice.type) {
        AdviceType.UMBRELLA -> Color(0xFFF5F9FF)
        AdviceType.SUNSCREEN -> Color(0xFFFFF3F8)
        AdviceType.HYDRATE -> Color(0xFFF2FCFE)
        AdviceType.LAYER_UP -> Color(0xFFF7F3FF)
        AdviceType.WIND_CARE -> Color(0xFFF2FCF8)
        AdviceType.READY -> Color(0xFFFFF8E7)
    }

    // A subtle repeating animation draws attention to the main advice headline
    // without making the card feel distracting or too flashy.
    val transition = rememberInfiniteTransition(label = "adviceBlink")

    val blinkProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850),
            repeatMode = RepeatMode.Reverse
        ),
        label = "adviceBlinkProgress"
    )

    // The headline color gently moves between the normal text color and the
    // advice accent color, helping the key message stand out.
    val headlineColor = lerp(
        start = MaterialTheme.colorScheme.onSurface,
        stop = accentColor,
        fraction = blinkProgress
    )

    ElevatedCard(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = backgroundTop
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            backgroundTop,
                            Color.White
                        )
                    )
                )
                .padding(
                    horizontal = if (expanded) 22.dp else 13.dp,
                    vertical = if (expanded) 18.dp else 13.dp
                )
        ) {
            // The badge stays at the top-right to label the advice category
            // while keeping the main message area clean and readable.
            Text(
                text = advice.visualLabel,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = accentColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = accentColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .padding(horizontal = 9.dp, vertical = 4.dp)
            )

            // In expanded mode, the text block is vertically centered so the
            // full-width advice card does not look empty or top-heavy.
            Column(
                modifier = Modifier
                    .align(
                        if (expanded) Alignment.CenterStart else Alignment.TopStart
                    )
                    .fillMaxWidth(
                        if (expanded) 0.88f else 1f
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    if (expanded) 8.dp else 6.dp
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Advice",
                        style = if (expanded) {
                            MaterialTheme.typography.titleSmall
                        } else {
                            MaterialTheme.typography.labelLarge
                        },
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // The expanded layout uses a larger headline.
                Text(
                    text = advice.headline,
                    style = if (expanded) {
                        MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 24.sp,
                            lineHeight = 28.sp
                        )
                    } else if (advice.type == AdviceType.READY) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.titleMedium.copy(fontSize = 19.sp)
                    },
                    fontWeight = FontWeight.Bold,
                    color = headlineColor
                )

                // The detail text is optional so the Settings screen can switch
                // between concise advice and longer go-out guidance.
                if (showDetail) {
                    Text(
                        text = advice.detail,
                        style = if (expanded) {
                            MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                lineHeight = 21.sp,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            )
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}