package com.k9t.scoreknight.football.domain.usecase

import com.k9t.scoreknight.football.domain.model.FootballMatchDetails
import com.k9t.scoreknight.football.domain.repository.FootballRepository
import javax.inject.Inject

class GetFootballMatchDetailsUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(id: String): Result<FootballMatchDetails> {
        return repository.getMatchDetails(id)
    }
}
