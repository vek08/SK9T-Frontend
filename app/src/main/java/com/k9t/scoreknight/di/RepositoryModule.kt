package com.k9t.scoreknight.di

import com.k9t.scoreknight.data.repository.CricketRepositoryImpl
import com.k9t.scoreknight.domain.repository.CricketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCricketRepository(
        cricketRepositoryImpl: CricketRepositoryImpl
    ): CricketRepository
}
