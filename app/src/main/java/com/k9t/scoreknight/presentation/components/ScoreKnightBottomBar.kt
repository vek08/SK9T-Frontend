package com.k9t.scoreknight.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class BottomTab(val title: String, val icon: ImageVector, val route: String) {
    HOME("Home", Icons.Rounded.Home, "home"),
    MATCHES("Matches", Icons.AutoMirrored.Rounded.List, "matches"),
    LIVE("Live", Icons.Rounded.PlayArrow, "live"),
    FAVORITES("Favorites", Icons.Rounded.Favorite, "favorites"),
    PROFILE("Profile", Icons.Rounded.Person, "profile")
}

@Composable
fun ScoreKnightBottomBar(
    currentRoute: String,
    onNavigate: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                    spotColor = Color.Black.copy(alpha = 0.05f),
                    ambientColor = Color.Black.copy(alpha = 0.02f)
                )
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE2E8F0),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomTab.values().forEach { tab ->
                    val isSelected = currentRoute == tab.route
                    BottomTabItem(
                        tab = tab,
                        isSelected = isSelected,
                        onClick = { onNavigate(tab) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomTabItem(
    tab: BottomTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0369A1) else Color(0xFF0F172A).copy(alpha = 0.6f),
        label = "iconColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0369A1) else Color(0xFF0F172A).copy(alpha = 0.6f),
        label = "textColor"
    )

    val bgColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0369A1).copy(alpha = 0.1f) else Color.Transparent,
        label = "bgColor"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF38BDF8).copy(alpha = 0.3f) else Color.Transparent,
        label = "borderColor"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .scale(scale)
                .size(36.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(1.dp, borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = tab.icon,
                contentDescription = tab.title,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = tab.title,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 10.sp
        )
    }
}

