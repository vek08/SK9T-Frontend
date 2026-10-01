package com.k9t.scoreknight.data.local;

import com.k9t.scoreknight.auth.data.FavoritesApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FavoritesManager_Factory implements Factory<FavoritesManager> {
  private final Provider<FavoritesApiService> apiProvider;

  public FavoritesManager_Factory(Provider<FavoritesApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public FavoritesManager get() {
    return newInstance(apiProvider.get());
  }

  public static FavoritesManager_Factory create(Provider<FavoritesApiService> apiProvider) {
    return new FavoritesManager_Factory(apiProvider);
  }

  public static FavoritesManager newInstance(FavoritesApiService api) {
    return new FavoritesManager(api);
  }
}
