package com.k9t.scoreknight.football.data.repository;

import com.k9t.scoreknight.football.data.remote.api.FootballApiService;
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
public final class FootballRepositoryImpl_Factory implements Factory<FootballRepositoryImpl> {
  private final Provider<FootballApiService> apiServiceProvider;

  public FootballRepositoryImpl_Factory(Provider<FootballApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public FootballRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static FootballRepositoryImpl_Factory create(
      Provider<FootballApiService> apiServiceProvider) {
    return new FootballRepositoryImpl_Factory(apiServiceProvider);
  }

  public static FootballRepositoryImpl newInstance(FootballApiService apiService) {
    return new FootballRepositoryImpl(apiService);
  }
}
