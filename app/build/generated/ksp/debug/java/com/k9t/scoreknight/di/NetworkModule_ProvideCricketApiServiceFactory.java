package com.k9t.scoreknight.di;

import com.k9t.scoreknight.data.remote.api.CricketApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideCricketApiServiceFactory implements Factory<CricketApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideCricketApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public CricketApiService get() {
    return provideCricketApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideCricketApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideCricketApiServiceFactory(retrofitProvider);
  }

  public static CricketApiService provideCricketApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCricketApiService(retrofit));
  }
}
