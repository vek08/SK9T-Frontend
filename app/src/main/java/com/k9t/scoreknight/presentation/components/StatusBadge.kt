package com.k9t.scoreknight.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.k9t.scoreknight.ui.theme.CompletedIndicatorColor
import com.k9t.scoreknight.ui.theme.LiveIndicatorColor
import com.k9t.scoreknight.ui.theme.UpcomingIndicatorColor

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val statusLower = status.lowercase()
    val (backgroundColor, textColor) = when {
        statusLower.contains("live") || statusLower.contains("inning") || statusLower.contains("stumps") -> 
            Pair(LiveIndicatorColor.copy(alpha = 0.15f), LiveIndicatorColor)
        statusLower.contains("upcoming") || statusLower.contains("yet to begin") -> 
            Pair(UpcomingIndicatorColor.copy(alpha = 0.15f), UpcomingIndicatorColor)
        else -> 
            Pair(CompletedIndicatorColor.copy(alpha = 0.15f), CompletedIndicatorColor)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
