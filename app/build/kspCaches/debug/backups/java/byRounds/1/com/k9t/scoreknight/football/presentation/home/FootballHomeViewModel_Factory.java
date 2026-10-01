package com.k9t.scoreknight.football.presentation.home;

import com.k9t.scoreknight.football.domain.usecase.GetFootballHomeMatchesUseCase;
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
public final class FootballHomeViewModel_Factory implements Factory<FootballHomeViewModel> {
  private final Provider<GetFootballHomeMatchesUseCase> getHomeMatchesUseCaseProvider;

  public FootballHomeViewModel_Factory(
      Provider<GetFootballHomeMatchesUseCase> getHomeMatchesUseCaseProvider) {
    this.getHomeMatchesUseCaseProvider = getHomeMatchesUseCaseProvider;
  }

  @Override
  public FootballHomeViewModel get() {
    return newInstance(getHomeMatchesUseCaseProvider.get());
  }

  public static FootballHomeViewModel_Factory create(
      Provider<GetFootballHomeMatchesUseCase> getHomeMatchesUseCaseProvider) {
    return new FootballHomeViewModel_Factory(getHomeMatchesUseCaseProvider);
  }

  public static FootballHomeViewModel newInstance(
      GetFootballHomeMatchesUseCase getHomeMatchesUseCase) {
    return new FootballHomeViewModel(getHomeMatchesUseCase);
  }
}
