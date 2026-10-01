package com.k9t.scoreknight.football.data.mapper

import com.k9t.scoreknight.football.data.remote.dto.FootballMatchCardDto
import com.k9t.scoreknight.football.domain.model.*
import com.k9t.scoreknight.football.data.remote.dto.*

fun FootballMatchCardDto.toDomain(type: String): FootballMatch {
    return FootballMatch(
        id = id?.toString() ?: "",
        competition = competition ?: "Unknown",
        competitionLogo = competitionLogo ?: "",
        homeTeam = homeTeam ?: "Unknown",
        awayTeam = awayTeam ?: "Unknown",
        homeTeamLogo = homeTeamLogo ?: "",
        awayTeamLogo = awayTeamLogo ?: "",
        homeScore = homeScore ?: "0",
        awayScore = awayScore ?: "0",
        status = status ?: "",
        elapsed = elapsed ?: "",
        venue = venue ?: "",
        matchDate = matchDate ?: "",
        type = type
    )
}

fun FootballEventDto.toDomain(): FootballEvent {
    return FootballEvent(
        timeElapsed = timeElapsed ?: "",
        teamName = teamName ?: "",
        teamLogo = teamLogo ?: "",
        playerName = playerName ?: "",
        assistName = assistName ?: "",
        type = type ?: "",
        detail = detail ?: ""
    )
}

fun FootballMatchDetailsDto.toDomain(): FootballMatchDetails {
    return FootballMatchDetails(
        match = match?.toDomain("details") ?: FootballMatchCardDto().toDomain("details"),
        events = events?.map { it.toDomain() } ?: emptyList()
    )
}

fun FootballStandingDto.toDomain(): FootballStanding {
    return FootballStanding(
        position = position ?: "",
        teamName = teamName ?: "",
        teamLogo = teamLogo ?: "",
        played = played ?: "0",
        won = won ?: "0",
        drawn = drawn ?: "0",
        lost = lost ?: "0",
        goalDifference = goalDifference ?: "0",
        points = points ?: "0"
    )
}
