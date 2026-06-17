package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceType
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain.AdviceUiModel

@Composable
fun AdviceTextCard(
    advice: AdviceUiModel,
    modifier: Modifier = Modifier
) {
    val accentColor = when (advice.type) {
        AdviceType.UMBRELLA -> Color(0xFF4F8DFD)
        AdviceType.SUNSCREEN -> Color(0xFFEC4899)
        AdviceType.HYDRATE -> Color(0xFF06B6D4)
        AdviceType.LAYER_UP -> Color(0xFF8B5CF6)
        AdviceType.WIND_CARE -> Color(0xFF10B981)
        AdviceType.READY -> Color(0xFFF59E0B)
    }

    val backgroundTop = when (advice.type) {
        AdviceType.UMBRELLA -> Color(0xFFF5F9FF)
        AdviceType.SUNSCREEN -> Color(0xFFFFF3F8)
        AdviceType.HYDRATE -> Color(0xFFF2FCFE)
        AdviceType.LAYER_UP -> Color(0xFFF7F3FF)
        AdviceType.WIND_CARE -> Color(0xFFF2FCF8)
        AdviceType.READY -> Color(0xFFFFF9EC)
    }

    val backgroundBottom = Color.White

    ElevatedCard(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(backgroundTop, backgroundBottom)
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Advice",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = advice.visualLabel,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor,
                    modifier = Modifier
                        .background(
                            color = accentColor.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(999.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Text(
                text = advice.headline,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = advice.detail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}