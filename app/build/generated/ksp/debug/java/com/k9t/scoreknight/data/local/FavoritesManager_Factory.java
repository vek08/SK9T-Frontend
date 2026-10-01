package com.k9t.scoreknight.data.local;

import com.k9t.scoreknight.auth.data.FavoritesApiService;
import com.k9t.scoreknight.auth.data.TokenManager;
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

  private final Provider<TokenManager> tokenManagerProvider;

  public FavoritesManager_Factory(Provider<FavoritesApiService> apiProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.apiProvider = apiProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public FavoritesManager get() {
    return newInstance(apiProvider.get(), tokenManagerProvider.get());
  }

  public static FavoritesManager_Factory create(Provider<FavoritesApiService> apiProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new FavoritesManager_Factory(apiProvider, tokenManagerProvider);
  }

  public static FavoritesManager newInstance(FavoritesApiService api, TokenManager tokenManager) {
    return new FavoritesManager(api, tokenManager);
  }
}
