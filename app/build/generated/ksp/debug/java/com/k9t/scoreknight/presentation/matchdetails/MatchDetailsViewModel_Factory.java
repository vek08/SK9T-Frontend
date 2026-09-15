package com.k9t.scoreknight.presentation.matchdetails;

import androidx.lifecycle.SavedStateHandle;
import com.k9t.scoreknight.data.local.FavoritesManager;
import com.k9t.scoreknight.domain.repository.CricketRepository;
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
public final class MatchDetailsViewModel_Factory implements Factory<MatchDetailsViewModel> {
  private final Provider<CricketRepository> repositoryProvider;

  private final Provider<FavoritesManager> favoritesManagerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public MatchDetailsViewModel_Factory(Provider<CricketRepository> repositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.favoritesManagerProvider = favoritesManagerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public MatchDetailsViewModel get() {
    return newInstance(repositoryProvider.get(), favoritesManagerProvider.get(), savedStateHandleProvider.get());
  }

  public static MatchDetailsViewModel_Factory create(Provider<CricketRepository> repositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new MatchDetailsViewModel_Factory(repositoryProvider, favoritesManagerProvider, savedStateHandleProvider);
  }

  public static MatchDetailsViewModel newInstance(CricketRepository repository,
      FavoritesManager favoritesManager, SavedStateHandle savedStateHandle) {
    return new MatchDetailsViewModel(repository, favoritesManager, savedStateHandle);
  }
}
