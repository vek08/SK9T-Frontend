package com.k9t.scoreknight.football.presentation.details;

import androidx.lifecycle.SavedStateHandle;
import com.k9t.scoreknight.football.domain.usecase.GetFootballMatchDetailsUseCase;
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
public final class FootballMatchDetailsViewModel_Factory implements Factory<FootballMatchDetailsViewModel> {
  private final Provider<GetFootballMatchDetailsUseCase> getDetailsUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public FootballMatchDetailsViewModel_Factory(
      Provider<GetFootballMatchDetailsUseCase> getDetailsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.getDetailsUseCaseProvider = getDetailsUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public FootballMatchDetailsViewModel get() {
    return newInstance(getDetailsUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static FootballMatchDetailsViewModel_Factory create(
      Provider<GetFootballMatchDetailsUseCase> getDetailsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new FootballMatchDetailsViewModel_Factory(getDetailsUseCaseProvider, savedStateHandleProvider);
  }

  public static FootballMatchDetailsViewModel newInstance(
      GetFootballMatchDetailsUseCase getDetailsUseCase, SavedStateHandle savedStateHandle) {
    return new FootballMatchDetailsViewModel(getDetailsUseCase, savedStateHandle);
  }
}
