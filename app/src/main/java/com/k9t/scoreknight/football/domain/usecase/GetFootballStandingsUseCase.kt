package com.k9t.scoreknight.football.domain.usecase

import com.k9t.scoreknight.football.domain.model.FootballStanding
import com.k9t.scoreknight.football.domain.repository.FootballRepository
import javax.inject.Inject

class GetFootballStandingsUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(): Result<List<FootballStanding>> {
        return repository.getStandings()
    }
}
