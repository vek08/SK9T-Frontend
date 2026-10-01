package com.k9t.scoreknight.football.presentation.matches;

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
public final class FootballMatchesViewModel_Factory implements Factory<FootballMatchesViewModel> {
  private final Provider<FootballRepository> repositoryProvider;

  public FootballMatchesViewModel_Factory(Provider<FootballRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FootballMatchesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static FootballMatchesViewModel_Factory create(
      Provider<FootballRepository> repositoryProvider) {
    return new FootballMatchesViewModel_Factory(repositoryProvider);
  }

  public static FootballMatchesViewModel newInstance(FootballRepository repository) {
    return new FootballMatchesViewModel(repository);
  }
}
