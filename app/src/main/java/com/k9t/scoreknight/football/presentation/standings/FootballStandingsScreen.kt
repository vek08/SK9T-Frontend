package com.k9t.scoreknight.football.presentation.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FootballStandingsScreen(
    viewModel: FootballStandingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        Text(
            text = "Standings",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
            when (val state = uiState) {
                is FootballStandingsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFC0F050))
                }
                is FootballStandingsUiState.Error -> {
                    Text(state.message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                is FootballStandingsUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1E293B))
                                    .padding(16.dp)
                            ) {
                                Text("POS", color = Color.LightGray, modifier = Modifier.weight(0.15f))
                                Text("TEAM", color = Color.LightGray, modifier = Modifier.weight(0.4f))
                                Text("P", color = Color.LightGray, modifier = Modifier.weight(0.1f))
                                Text("GD", color = Color.LightGray, modifier = Modifier.weight(0.15f))
                                Text("PTS", color = Color.LightGray, modifier = Modifier.weight(0.15f))
                            }
                        }
                        items(state.standings) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(item.position, color = Color.White, modifier = Modifier.weight(0.15f))
                                Text(item.teamName, color = Color.White, modifier = Modifier.weight(0.4f), maxLines = 1)
                                Text(item.played, color = Color.White, modifier = Modifier.weight(0.1f))
                                Text(item.goalDifference, color = Color.White, modifier = Modifier.weight(0.15f))
                                Text(item.points, color = Color(0xFFC0F050), fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
                            }
                            HorizontalDivider(color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}
