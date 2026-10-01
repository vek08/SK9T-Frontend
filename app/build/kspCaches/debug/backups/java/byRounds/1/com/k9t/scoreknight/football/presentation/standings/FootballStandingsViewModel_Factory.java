package com.k9t.scoreknight.football.presentation.standings;

import com.k9t.scoreknight.football.domain.usecase.GetFootballStandingsUseCase;
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
public final class FootballStandingsViewModel_Factory implements Factory<FootballStandingsViewModel> {
  private final Provider<GetFootballStandingsUseCase> getStandingsUseCaseProvider;

  public FootballStandingsViewModel_Factory(
      Provider<GetFootballStandingsUseCase> getStandingsUseCaseProvider) {
    this.getStandingsUseCaseProvider = getStandingsUseCaseProvider;
  }

  @Override
  public FootballStandingsViewModel get() {
    return newInstance(getStandingsUseCaseProvider.get());
  }

  public static FootballStandingsViewModel_Factory create(
      Provider<GetFootballStandingsUseCase> getStandingsUseCaseProvider) {
    return new FootballStandingsViewModel_Factory(getStandingsUseCaseProvider);
  }

  public static FootballStandingsViewModel newInstance(
      GetFootballStandingsUseCase getStandingsUseCase) {
    return new FootballStandingsViewModel(getStandingsUseCase);
  }
}
