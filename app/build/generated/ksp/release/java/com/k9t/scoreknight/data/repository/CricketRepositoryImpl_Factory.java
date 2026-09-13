package com.k9t.scoreknight.data.repository;

import com.k9t.scoreknight.data.remote.api.CricketApiService;
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
public final class CricketRepositoryImpl_Factory implements Factory<CricketRepositoryImpl> {
  private final Provider<CricketApiService> apiServiceProvider;

  public CricketRepositoryImpl_Factory(Provider<CricketApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public CricketRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static CricketRepositoryImpl_Factory create(
      Provider<CricketApiService> apiServiceProvider) {
    return new CricketRepositoryImpl_Factory(apiServiceProvider);
  }

  public static CricketRepositoryImpl newInstance(CricketApiService apiService) {
    return new CricketRepositoryImpl(apiService);
  }
}
