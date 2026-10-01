package com.k9t.scoreknight.football.domain.usecase

import com.k9t.scoreknight.football.domain.model.FootballMatch
import com.k9t.scoreknight.football.domain.repository.FootballRepository
import javax.inject.Inject

class GetFootballHomeMatchesUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(): Result<Map<String, List<FootballMatch>>> {
        return repository.getHomeMatches()
    }
}
