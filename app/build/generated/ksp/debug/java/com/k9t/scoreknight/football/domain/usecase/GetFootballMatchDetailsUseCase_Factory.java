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
public final class GetFootballMatchDetailsUseCase_Factory implements Factory<GetFootballMatchDetailsUseCase> {
  private final Provider<FootballRepository> repositoryProvider;

  public GetFootballMatchDetailsUseCase_Factory(Provider<FootballRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFootballMatchDetailsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFootballMatchDetailsUseCase_Factory create(
      Provider<FootballRepository> repositoryProvider) {
    return new GetFootballMatchDetailsUseCase_Factory(repositoryProvider);
  }

  public static GetFootballMatchDetailsUseCase newInstance(FootballRepository repository) {
    return new GetFootballMatchDetailsUseCase(repository);
  }
}
