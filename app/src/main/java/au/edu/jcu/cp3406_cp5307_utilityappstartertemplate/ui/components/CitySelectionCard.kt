package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CitySelectionCard(
    cities: List<String>,
    selectedCity: String,
    onCitySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF9FBFF),
                            Color(0xFFF3F6FC)
                        )
                    )
                )
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "City",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF25283A)
            )

            // Two-column city layout keeps the settings screen compact and easy to scan.
            cities.chunked(2).forEach { rowCities ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowCities.forEach { city ->
                        CityOptionTile(
                            city = city,
                            selected = selectedCity == city,
                            onClick = { onCitySelected(city) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (rowCities.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CityOptionTile(
    city: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundBrush = if (selected) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFEAF4FF),
                Color(0xFFDCEBFF)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color.White,
                Color(0xFFF7F8FC)
            )
        )
    }

    val borderColor = if (selected) {
        Color(0xFF88B7FF)
    } else {
        Color(0xFFE4E9F2)
    }

    val textColor = if (selected) {
        Color(0xFF2F5F9F)
    } else {
        Color(0xFF3D4356)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(brush = backgroundBrush)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = if (selected) Color(0xFF6EA8F7) else Color(0xFFD6DCE8),
                    shape = CircleShape
                )
        )

        Text(
            text = city,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor,
            maxLines = 1
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6EA8F7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD6DCE8),
                        shape = CircleShape
                    )
            )
        }
    }
}

