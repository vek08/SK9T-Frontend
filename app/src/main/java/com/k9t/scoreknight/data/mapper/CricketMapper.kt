package com.k9t.scoreknight.data.mapper

import com.k9t.scoreknight.data.remote.dto.CricketMatchDto
import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.domain.model.InningScore
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun CricketMatchDto.toDomain(matchState: String = "FINISHED"): CricketMatch {
    val (scoreString, parsedScoresList) = parseScoreData(score)
    
    return CricketMatch(
        matchId = matchId ?: "",
        matchState = matchState,
        teamA = if (teamA.isNullOrBlank()) "Unknown Team" else teamA,
        teamB = if (teamB.isNullOrBlank()) "Unknown Team" else teamB,
        teamALogo = teamALogo,
        teamBLogo = teamBLogo,
        status = status ?: "Status Unavailable",
        score = scoreString,
        parsedScores = parsedScoresList,
        venue = venue ?: "Venue Unavailable",
        matchDate = formatDate(matchDate),
        matchType = matchType,
        squads = squads?.map { squadDto ->
            com.k9t.scoreknight.domain.model.MatchSquad(
                teamName = squadDto.teamName ?: "Unknown Team",
                img = squadDto.img,
                players = squadDto.players?.map { playerDto ->
                    com.k9t.scoreknight.domain.model.MatchPlayer(
                        id = playerDto.id ?: "",
                        name = playerDto.name ?: "Unknown Player",
                        role = playerDto.role ?: "",
                        battingStyle = playerDto.battingStyle ?: "",
                        bowlingStyle = playerDto.bowlingStyle ?: "",
                        country = playerDto.country ?: "",
                        playerImg = playerDto.playerImg
                    )
                } ?: emptyList()
            )
        }
    )
}

private fun formatDate(dateStr: String?): String {
    if (dateStr.isNullOrBlank()) return ""
    return try {
        if (dateStr.contains("T")) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("GMT")
            val date = inputFormat.parse(dateStr)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
            date?.let { outputFormat.format(it) } ?: dateStr
        } else {
            dateStr
        }
    } catch (e: Exception) {
        dateStr
    }
}

private fun parseScoreData(scoreJson: String?): Pair<String?, List<InningScore>?> {
    if (scoreJson.isNullOrBlank()) return Pair(null, null)
    
    return try {
        if (scoreJson.trim().startsWith("[")) {
            val jsonArray = JSONArray(scoreJson)
            val scoreStrings = mutableListOf<String>()
            val inningScores = mutableListOf<InningScore>()
            
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val runs = item.optString("r", "0")
                val wickets = item.optString("w", "0")
                val overs = item.optString("o", "0.0")
                var inning = item.optString("inning", "").replace(" Inning", "").trim()
                
                // Clean up inning name (e.g., "Pakistan 1" -> "Pakistan")
                if (inning.endsWith(" 1") || inning.endsWith(" 2")) {
                    inning = inning.dropLast(2).trim()
                }
                
                val scoreDetails = "${runs}-${wickets} (${overs})"
                
                if (inning.isNotBlank()) {
                    scoreStrings.add("${inning}\n${scoreDetails}")
                    inningScores.add(InningScore(inning, scoreDetails))
                } else {
                    scoreStrings.add(scoreDetails)
                    inningScores.add(InningScore("Unknown", scoreDetails))
                }
            }
            
            val finalString = if (scoreStrings.isNotEmpty()) scoreStrings.joinToString("\n\n") else scoreJson
            val finalList = if (inningScores.isNotEmpty()) inningScores else null
            
            Pair(finalString, finalList)
        } else {
            if (scoreJson.contains("{") || scoreJson.contains("[")) {
                Pair(null, null)
            } else {
                Pair(scoreJson, null)
            }
        }
    } catch (e: Exception) {
        if (scoreJson.contains("{") || scoreJson.contains("[")) {
            Pair(null, null)
        } else {
            Pair(scoreJson, null)
        }
    }
}
