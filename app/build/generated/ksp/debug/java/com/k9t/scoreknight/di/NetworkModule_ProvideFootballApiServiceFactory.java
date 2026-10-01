package com.k9t.scoreknight.di;

import com.k9t.scoreknight.football.data.remote.api.FootballApiService;
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
public final class NetworkModule_ProvideFootballApiServiceFactory implements Factory<FootballApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideFootballApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public FootballApiService get() {
    return provideFootballApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideFootballApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideFootballApiServiceFactory(retrofitProvider);
  }

  public static FootballApiService provideFootballApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFootballApiService(retrofit));
  }
}
