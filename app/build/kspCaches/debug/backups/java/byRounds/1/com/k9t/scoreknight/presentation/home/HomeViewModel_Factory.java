package com.k9t.scoreknight.presentation.home;

import com.k9t.scoreknight.data.local.FavoritesManager;
import com.k9t.scoreknight.domain.usecase.GetHomeMatchesUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider;

  private final Provider<FavoritesManager> favoritesManagerProvider;

  public HomeViewModel_Factory(Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    this.getHomeMatchesUseCaseProvider = getHomeMatchesUseCaseProvider;
    this.favoritesManagerProvider = favoritesManagerProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(getHomeMatchesUseCaseProvider.get(), favoritesManagerProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    return new HomeViewModel_Factory(getHomeMatchesUseCaseProvider, favoritesManagerProvider);
  }

  public static HomeViewModel newInstance(GetHomeMatchesUseCase getHomeMatchesUseCase,
      FavoritesManager favoritesManager) {
    return new HomeViewModel(getHomeMatchesUseCase, favoritesManager);
  }
}
