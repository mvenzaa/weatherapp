package com.venza.wheaterapp.di.modules

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.venza.wheaterapp.db.CityDao
import com.venza.wheaterapp.db.ForecastCityDao
import com.venza.wheaterapp.domain.api.ApiGooglePlaces
import com.venza.wheaterapp.domain.api.ApiService
import com.venza.wheaterapp.ui.details.repo.DetailsRepository
import com.venza.wheaterapp.ui.details.repo.DetailsRepositoryImp
import com.venza.wheaterapp.ui.home.data.LocationProvider
import com.venza.wheaterapp.ui.home.repo.HomeRepository
import com.venza.wheaterapp.ui.home.repo.HomeRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application.applicationContext)
    }

    @Provides
    fun provideLocationProvider(context: Application, client: FusedLocationProviderClient
    ): LocationProvider {
        return LocationProvider(context.applicationContext, client)
    }

    @Provides
    fun providesHomeRepository(homeRepositoryImp: HomeRepositoryImp, locationProvider: LocationProvider): HomeRepository {
        return HomeRepository(homeRepositoryImp , locationProvider)
    }

    @Provides
    fun providesHomeRepositoryImp(apiService: ApiService, apiGooglePlaces : ApiGooglePlaces, cityDao : CityDao): HomeRepositoryImp {
        return HomeRepositoryImp(apiService  , apiGooglePlaces  , cityDao)
    }

    @Provides
    fun providesDetailsRepository(detailsRepositoryImp: DetailsRepositoryImp): DetailsRepository {
        return DetailsRepository(detailsRepositoryImp )
    }

    @Provides
    fun providesDetailsRepositoryImp(apiService: ApiService , forecastCityDao : ForecastCityDao): DetailsRepositoryImp {
        return DetailsRepositoryImp(apiService , forecastCityDao)
    }
}