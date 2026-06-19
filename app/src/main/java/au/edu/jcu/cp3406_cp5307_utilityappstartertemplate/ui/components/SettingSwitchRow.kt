package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingSwitchRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 72.dp)
            .background(
                // Each setting is displayed as an independent soft blue rounded card.
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF7FBFF),
                        Color(0xFFEAF4FF)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                // A subtle white border keeps each setting card light and clean.
                width = 1.dp,
                color = Color.White.copy(alpha = 0.95f),
                shape = RoundedCornerShape(20.dp)
            )
            // The whole row is clickable, not only the switch.
            .clickable { onCheckedChange(!checked) }
            .padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2F42),
                maxLines = 1
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    // Slightly larger text and line height improve readability on pale blue cards.
                    fontSize = 11.5.sp,
                    lineHeight = 15.sp
                ),
                // A deeper blue-grey keeps the description readable without looking too dark.
                color = Color(0xFF4F5A70),
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.width(52.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF78AFFF),
                checkedBorderColor = Color(0xFF78AFFF),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFDCE7F6),
                uncheckedBorderColor = Color(0xFFD0DBEB)
            )
        )
    }
}