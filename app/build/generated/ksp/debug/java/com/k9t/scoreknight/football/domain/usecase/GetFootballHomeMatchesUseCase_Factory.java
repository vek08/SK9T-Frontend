package com.k9t.scoreknight.football.domain.usecase;

import com.k9t.scoreknight.football.domain.repository.FootballRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class GetFootballHomeMatchesUseCase_Factory implements Factory<GetFootballHomeMatchesUseCase> {
  private final Provider<FootballRepository> repositoryProvider;

  public GetFootballHomeMatchesUseCase_Factory(Provider<FootballRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFootballHomeMatchesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFootballHomeMatchesUseCase_Factory create(
      Provider<FootballRepository> repositoryProvider) {
    return new GetFootballHomeMatchesUseCase_Factory(repositoryProvider);
  }

  public static GetFootballHomeMatchesUseCase newInstance(FootballRepository repository) {
    return new GetFootballHomeMatchesUseCase(repository);
  }
}
