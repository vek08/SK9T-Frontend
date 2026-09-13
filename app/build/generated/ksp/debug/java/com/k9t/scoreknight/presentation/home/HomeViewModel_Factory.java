package com.k9t.scoreknight.presentation.home;

import com.k9t.scoreknight.domain.usecase.GetHomeMatchesUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider;

  public HomeViewModel_Factory(Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider) {
    this.getHomeMatchesUseCaseProvider = getHomeMatchesUseCaseProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(getHomeMatchesUseCaseProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetHomeMatchesUseCase> getHomeMatchesUseCaseProvider) {
    return new HomeViewModel_Factory(getHomeMatchesUseCaseProvider);
  }

  public static HomeViewModel newInstance(GetHomeMatchesUseCase getHomeMatchesUseCase) {
    return new HomeViewModel(getHomeMatchesUseCase);
  }
}
