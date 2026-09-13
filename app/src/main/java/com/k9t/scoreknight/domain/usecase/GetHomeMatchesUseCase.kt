package com.k9t.scoreknight.domain.usecase

import com.k9t.scoreknight.domain.model.CricketMatch
import com.k9t.scoreknight.domain.repository.CricketRepository
import javax.inject.Inject

class GetHomeMatchesUseCase @Inject constructor(
    private val repository: CricketRepository
) {
    suspend operator fun invoke(): Result<List<CricketMatch>> {
        return repository.getHomeMatches()
    }
}
