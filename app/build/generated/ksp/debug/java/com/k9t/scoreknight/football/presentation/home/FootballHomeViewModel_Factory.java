package com.k9t.scoreknight.football.presentation.home;

import com.k9t.scoreknight.auth.data.TokenManager;
import com.k9t.scoreknight.data.local.FavoritesManager;
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

  private final Provider<FavoritesManager> favoritesManagerProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public FootballHomeViewModel_Factory(
      Provider<GetFootballHomeMatchesUseCase> getHomeMatchesUseCaseProvider,
      Provider<FavoritesManager> favoritesManagerProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.getHomeMatchesUseCaseProvider = getHomeMatchesUseCaseProvider;
    this.favoritesManagerProvider = favoritesManagerProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public FootballHomeViewModel get() {
    return newInstance(getHomeMatchesUseCaseProvider.get(), favoritesManagerProvider.get(), tokenManagerProvider.get());
  }

  public static FootballHomeViewModel_Factory create(
      Provider<GetFootballHomeMatchesUseCase> getHomeMatchesUseCaseProvider,
      Provider<FavoritesManager> favoritesManagerProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new FootballHomeViewModel_Factory(getHomeMatchesUseCaseProvider, favoritesManagerProvider, tokenManagerProvider);
  }

  public static FootballHomeViewModel newInstance(
      GetFootballHomeMatchesUseCase getHomeMatchesUseCase, FavoritesManager favoritesManager,
      TokenManager tokenManager) {
    return new FootballHomeViewModel(getHomeMatchesUseCase, favoritesManager, tokenManager);
  }
}
