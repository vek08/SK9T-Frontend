package com.k9t.scoreknight.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.k9t.scoreknight.domain.model.CricketMatch

@Composable
fun MatchCard(
    match: CricketMatch,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(340.dp)
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- TOP ROW: Match Info & Status Badge ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Header Info (e.g. 1st T20I • Venue • Date)
                val headerInfo = listOf(match.venue, match.matchDate)
                    .filter { it.isNotBlank() && it != "Venue Unavailable" }
                    .joinToString(" • ")
                
                Text(
                    text = headerInfo.ifBlank { "Match Info Unavailable" },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                StatusBadge(status = match.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // --- MIDDLE ROW: Teams and Scores ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // If we have nicely parsed structured scores
                if (match.parsedScores != null && match.parsedScores.isNotEmpty()) {
                    // Try to map parsed scores to Team A and Team B
                    // If parsing is messy, we just iterate through up to 2 items
                    val firstScore = match.parsedScores.getOrNull(0)
                    val secondScore = match.parsedScores.getOrNull(1)

                    TeamScoreRow(
                        teamName = match.teamA,
                        logoUrl = match.teamALogo,
                        scoreText = if (firstScore?.inningName?.contains(match.teamA, ignoreCase = true) == true) {
                            firstScore.scoreDetails
                        } else if (secondScore?.inningName?.contains(match.teamA, ignoreCase = true) == true) {
                            secondScore.scoreDetails
                        } else {
                            firstScore?.scoreDetails ?: ""
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TeamScoreRow(
                        teamName = match.teamB,
                        logoUrl = match.teamBLogo,
                        scoreText = if (secondScore?.inningName?.contains(match.teamB, ignoreCase = true) == true) {
                            secondScore.scoreDetails
                        } else if (firstScore?.inningName?.contains(match.teamB, ignoreCase = true) == true) {
                            firstScore.scoreDetails
                        } else {
                            secondScore?.scoreDetails ?: ""
                        }
                    )
                } else {
                    // Fallback to basic team display if scores aren't easily parsed
                    TeamScoreRow(teamName = match.teamA, logoUrl = match.teamALogo, scoreText = "")
                    Spacer(modifier = Modifier.height(8.dp))
                    TeamScoreRow(teamName = match.teamB, logoUrl = match.teamBLogo, scoreText = "")
                }
            }

            // --- BOTTOM ROW: Result and Favorite ---
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (match.status.lowercase().contains("live")) {
                        "Match in progress..."
                    } else if (match.status.lowercase().contains("upcoming")) {
                        "Match hasn't started yet"
                    } else {
                        // Usually the result is implicitly described or we just say Completed
                        "Completed"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = if (match.status.lowercase().contains("live")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onFavoriteClick() }
                )
            }
        }
    }
}

@Composable
private fun TeamScoreRow(
    teamName: String,
    logoUrl: String?,
    scoreText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Flag/Logo
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = logoUrl,
                    contentDescription = "$teamName Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Team Name
            Text(
                text = teamName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        // Score
        if (scoreText.isNotBlank()) {
            Text(
                text = scoreText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
