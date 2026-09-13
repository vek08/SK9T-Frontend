package com.k9t.scoreknight.domain.usecase;

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
public final class GetHomeMatchesUseCase_Factory implements Factory<GetHomeMatchesUseCase> {
  private final Provider<CricketRepository> repositoryProvider;

  public GetHomeMatchesUseCase_Factory(Provider<CricketRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetHomeMatchesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetHomeMatchesUseCase_Factory create(
      Provider<CricketRepository> repositoryProvider) {
    return new GetHomeMatchesUseCase_Factory(repositoryProvider);
  }

  public static GetHomeMatchesUseCase newInstance(CricketRepository repository) {
    return new GetHomeMatchesUseCase(repository);
  }
}
