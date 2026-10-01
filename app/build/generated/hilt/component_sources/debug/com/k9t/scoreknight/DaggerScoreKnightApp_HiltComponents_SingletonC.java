package com.k9t.scoreknight;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.k9t.scoreknight.auth.data.AuthApiService;
import com.k9t.scoreknight.auth.data.AuthInterceptor;
import com.k9t.scoreknight.auth.data.AuthRepository;
import com.k9t.scoreknight.auth.data.FavoritesApiService;
import com.k9t.scoreknight.auth.data.TokenManager;
import com.k9t.scoreknight.auth.presentation.AuthViewModel;
import com.k9t.scoreknight.auth.presentation.AuthViewModel_HiltModules;
import com.k9t.scoreknight.data.local.FavoritesManager;
import com.k9t.scoreknight.data.remote.api.CricketApiService;
import com.k9t.scoreknight.data.repository.CricketRepositoryImpl;
import com.k9t.scoreknight.di.NetworkModule_ProvideAuthApiServiceFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideCricketApiServiceFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideFavoritesApiServiceFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideFootballApiServiceFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideJsonFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideOkHttpClientFactory;
import com.k9t.scoreknight.di.NetworkModule_ProvideRetrofitFactory;
import com.k9t.scoreknight.domain.repository.CricketRepository;
import com.k9t.scoreknight.domain.usecase.GetHomeMatchesUseCase;
import com.k9t.scoreknight.football.data.remote.api.FootballApiService;
import com.k9t.scoreknight.football.data.repository.FootballRepositoryImpl;
import com.k9t.scoreknight.football.domain.repository.FootballRepository;
import com.k9t.scoreknight.football.domain.usecase.GetFootballHomeMatchesUseCase;
import com.k9t.scoreknight.football.domain.usecase.GetFootballMatchDetailsUseCase;
import com.k9t.scoreknight.football.domain.usecase.GetFootballStandingsUseCase;
import com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel;
import com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel_HiltModules;
import com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel;
import com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel_HiltModules;
import com.k9t.scoreknight.football.presentation.matches.FootballMatchesViewModel;
import com.k9t.scoreknight.football.presentation.matches.FootballMatchesViewModel_HiltModules;
import com.k9t.scoreknight.football.presentation.standings.FootballStandingsViewModel;
import com.k9t.scoreknight.football.presentation.standings.FootballStandingsViewModel_HiltModules;
import com.k9t.scoreknight.presentation.home.HomeViewModel;
import com.k9t.scoreknight.presentation.home.HomeViewModel_HiltModules;
import com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel;
import com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerScoreKnightApp_HiltComponents_SingletonC {
  private DaggerScoreKnightApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public ScoreKnightApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements ScoreKnightApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements ScoreKnightApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements ScoreKnightApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements ScoreKnightApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements ScoreKnightApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements ScoreKnightApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements ScoreKnightApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public ScoreKnightApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends ScoreKnightApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends ScoreKnightApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends ScoreKnightApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends ScoreKnightApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(7).put(LazyClassKeyProvider.com_k9t_scoreknight_auth_presentation_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel, FootballHomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel, FootballMatchDetailsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel, FootballMatchesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel, FootballStandingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_presentation_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel, MatchDetailsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel = "com.k9t.scoreknight.football.presentation.standings.FootballStandingsViewModel";

      static String com_k9t_scoreknight_presentation_home_HomeViewModel = "com.k9t.scoreknight.presentation.home.HomeViewModel";

      static String com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel = "com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel";

      static String com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel = "com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel";

      static String com_k9t_scoreknight_auth_presentation_AuthViewModel = "com.k9t.scoreknight.auth.presentation.AuthViewModel";

      static String com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel = "com.k9t.scoreknight.football.presentation.matches.FootballMatchesViewModel";

      static String com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel = "com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel";

      @KeepFieldType
      FootballStandingsViewModel com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel2;

      @KeepFieldType
      HomeViewModel com_k9t_scoreknight_presentation_home_HomeViewModel2;

      @KeepFieldType
      FootballHomeViewModel com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel2;

      @KeepFieldType
      FootballMatchDetailsViewModel com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel2;

      @KeepFieldType
      AuthViewModel com_k9t_scoreknight_auth_presentation_AuthViewModel2;

      @KeepFieldType
      FootballMatchesViewModel com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel2;

      @KeepFieldType
      MatchDetailsViewModel com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends ScoreKnightApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<FootballHomeViewModel> footballHomeViewModelProvider;

    private Provider<FootballMatchDetailsViewModel> footballMatchDetailsViewModelProvider;

    private Provider<FootballMatchesViewModel> footballMatchesViewModelProvider;

    private Provider<FootballStandingsViewModel> footballStandingsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MatchDetailsViewModel> matchDetailsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetFootballHomeMatchesUseCase getFootballHomeMatchesUseCase() {
      return new GetFootballHomeMatchesUseCase(singletonCImpl.bindFootballRepositoryProvider.get());
    }

    private GetFootballMatchDetailsUseCase getFootballMatchDetailsUseCase() {
      return new GetFootballMatchDetailsUseCase(singletonCImpl.bindFootballRepositoryProvider.get());
    }

    private GetFootballStandingsUseCase getFootballStandingsUseCase() {
      return new GetFootballStandingsUseCase(singletonCImpl.bindFootballRepositoryProvider.get());
    }

    private GetHomeMatchesUseCase getHomeMatchesUseCase() {
      return new GetHomeMatchesUseCase(singletonCImpl.bindCricketRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.footballHomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.footballMatchDetailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.footballMatchesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.footballStandingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.matchDetailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(7).put(LazyClassKeyProvider.com_k9t_scoreknight_auth_presentation_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel, ((Provider) footballHomeViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel, ((Provider) footballMatchDetailsViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel, ((Provider) footballMatchesViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel, ((Provider) footballStandingsViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_presentation_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel, ((Provider) matchDetailsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel = "com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel";

      static String com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel = "com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel";

      static String com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel = "com.k9t.scoreknight.football.presentation.standings.FootballStandingsViewModel";

      static String com_k9t_scoreknight_auth_presentation_AuthViewModel = "com.k9t.scoreknight.auth.presentation.AuthViewModel";

      static String com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel = "com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel";

      static String com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel = "com.k9t.scoreknight.football.presentation.matches.FootballMatchesViewModel";

      static String com_k9t_scoreknight_presentation_home_HomeViewModel = "com.k9t.scoreknight.presentation.home.HomeViewModel";

      @KeepFieldType
      MatchDetailsViewModel com_k9t_scoreknight_presentation_matchdetails_MatchDetailsViewModel2;

      @KeepFieldType
      FootballHomeViewModel com_k9t_scoreknight_football_presentation_home_FootballHomeViewModel2;

      @KeepFieldType
      FootballStandingsViewModel com_k9t_scoreknight_football_presentation_standings_FootballStandingsViewModel2;

      @KeepFieldType
      AuthViewModel com_k9t_scoreknight_auth_presentation_AuthViewModel2;

      @KeepFieldType
      FootballMatchDetailsViewModel com_k9t_scoreknight_football_presentation_details_FootballMatchDetailsViewModel2;

      @KeepFieldType
      FootballMatchesViewModel com_k9t_scoreknight_football_presentation_matches_FootballMatchesViewModel2;

      @KeepFieldType
      HomeViewModel com_k9t_scoreknight_presentation_home_HomeViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.k9t.scoreknight.auth.presentation.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 1: // com.k9t.scoreknight.football.presentation.home.FootballHomeViewModel 
          return (T) new FootballHomeViewModel(viewModelCImpl.getFootballHomeMatchesUseCase());

          case 2: // com.k9t.scoreknight.football.presentation.details.FootballMatchDetailsViewModel 
          return (T) new FootballMatchDetailsViewModel(viewModelCImpl.getFootballMatchDetailsUseCase(), viewModelCImpl.savedStateHandle);

          case 3: // com.k9t.scoreknight.football.presentation.matches.FootballMatchesViewModel 
          return (T) new FootballMatchesViewModel(singletonCImpl.bindFootballRepositoryProvider.get());

          case 4: // com.k9t.scoreknight.football.presentation.standings.FootballStandingsViewModel 
          return (T) new FootballStandingsViewModel(viewModelCImpl.getFootballStandingsUseCase());

          case 5: // com.k9t.scoreknight.presentation.home.HomeViewModel 
          return (T) new HomeViewModel(viewModelCImpl.getHomeMatchesUseCase(), singletonCImpl.favoritesManagerProvider.get(), singletonCImpl.tokenManagerProvider.get());

          case 6: // com.k9t.scoreknight.presentation.matchdetails.MatchDetailsViewModel 
          return (T) new MatchDetailsViewModel(singletonCImpl.bindCricketRepositoryProvider.get(), singletonCImpl.favoritesManagerProvider.get(), viewModelCImpl.savedStateHandle);

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends ScoreKnightApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends ScoreKnightApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends ScoreKnightApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<TokenManager> tokenManagerProvider;

    private Provider<AuthInterceptor> authInterceptorProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<AuthApiService> provideAuthApiServiceProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<FootballApiService> provideFootballApiServiceProvider;

    private Provider<FootballRepositoryImpl> footballRepositoryImplProvider;

    private Provider<FootballRepository> bindFootballRepositoryProvider;

    private Provider<CricketApiService> provideCricketApiServiceProvider;

    private Provider<CricketRepositoryImpl> cricketRepositoryImplProvider;

    private Provider<CricketRepository> bindCricketRepositoryProvider;

    private Provider<FavoritesApiService> provideFavoritesApiServiceProvider;

    private Provider<FavoritesManager> favoritesManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.tokenManagerProvider = DoubleCheck.provider(new SwitchingProvider<TokenManager>(singletonCImpl, 5));
      this.authInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<AuthInterceptor>(singletonCImpl, 4));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 6));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 2));
      this.provideAuthApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<AuthApiService>(singletonCImpl, 1));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 0));
      this.provideFootballApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<FootballApiService>(singletonCImpl, 8));
      this.footballRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 7);
      this.bindFootballRepositoryProvider = DoubleCheck.provider((Provider) footballRepositoryImplProvider);
      this.provideCricketApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<CricketApiService>(singletonCImpl, 10));
      this.cricketRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindCricketRepositoryProvider = DoubleCheck.provider((Provider) cricketRepositoryImplProvider);
      this.provideFavoritesApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<FavoritesApiService>(singletonCImpl, 12));
      this.favoritesManagerProvider = DoubleCheck.provider(new SwitchingProvider<FavoritesManager>(singletonCImpl, 11));
    }

    @Override
    public void injectScoreKnightApp(ScoreKnightApp arg0) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.k9t.scoreknight.auth.data.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideAuthApiServiceProvider.get(), singletonCImpl.tokenManagerProvider.get());

          case 1: // com.k9t.scoreknight.auth.data.AuthApiService 
          return (T) NetworkModule_ProvideAuthApiServiceFactory.provideAuthApiService(singletonCImpl.provideRetrofitProvider.get());

          case 2: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 3: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.authInterceptorProvider.get());

          case 4: // com.k9t.scoreknight.auth.data.AuthInterceptor 
          return (T) new AuthInterceptor(singletonCImpl.tokenManagerProvider.get());

          case 5: // com.k9t.scoreknight.auth.data.TokenManager 
          return (T) new TokenManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // kotlinx.serialization.json.Json 
          return (T) NetworkModule_ProvideJsonFactory.provideJson();

          case 7: // com.k9t.scoreknight.football.data.repository.FootballRepositoryImpl 
          return (T) new FootballRepositoryImpl(singletonCImpl.provideFootballApiServiceProvider.get());

          case 8: // com.k9t.scoreknight.football.data.remote.api.FootballApiService 
          return (T) NetworkModule_ProvideFootballApiServiceFactory.provideFootballApiService(singletonCImpl.provideRetrofitProvider.get());

          case 9: // com.k9t.scoreknight.data.repository.CricketRepositoryImpl 
          return (T) new CricketRepositoryImpl(singletonCImpl.provideCricketApiServiceProvider.get());

          case 10: // com.k9t.scoreknight.data.remote.api.CricketApiService 
          return (T) NetworkModule_ProvideCricketApiServiceFactory.provideCricketApiService(singletonCImpl.provideRetrofitProvider.get());

          case 11: // com.k9t.scoreknight.data.local.FavoritesManager 
          return (T) new FavoritesManager(singletonCImpl.provideFavoritesApiServiceProvider.get());

          case 12: // com.k9t.scoreknight.auth.data.FavoritesApiService 
          return (T) NetworkModule_ProvideFavoritesApiServiceFactory.provideFavoritesApiService(singletonCImpl.provideRetrofitProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
