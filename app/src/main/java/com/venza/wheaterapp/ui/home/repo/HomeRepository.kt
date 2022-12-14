package com.venza.wheaterapp.ui.home.repo

import com.venza.wheaterapp.data.ResultData
import com.venza.wheaterapp.db.CityModel
import com.venza.wheaterapp.domain.model.WeatherCityResponse
import com.venza.wheaterapp.domain.model.placeid.Location
import com.venza.wheaterapp.ui.home.data.LocationProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private val homeRepositoryImp: HomeRepositoryImp,
    private val useCase: LocationProvider,
) {

    // Retrofit Api
    fun fetchWeatherForLocation(city: String): Flow<ResultData<WeatherCityResponse>> = flow {
        emit(homeRepositoryImp.fetchWeatherForLocation(city))
    }

    fun getWeatherOfLatLon(): Flow<ResultData<WeatherCityResponse>> = flow {
        useCase.fetchLocation().collect { deviceLocation ->
            emit(homeRepositoryImp.getWeatherOfLatLon(deviceLocation.latitude.toString(),
                deviceLocation.longitude.toString()))
        }
    }

    fun getWeatherOfLatLon(location: Location?): Flow<ResultData<WeatherCityResponse>> = flow {
        emit(homeRepositoryImp.getWeatherOfLatLon(location?.lat.toString(),
            location?.lng.toString()))
    }

    // Google Places Api
    fun getGooglePlaces(searchText: String) = flow {
        if (searchText.length > 2) {
            delay(1000)
            emit(homeRepositoryImp.fetchPredictions(searchText))
        }
    }

    suspend fun getGooglePlaceOfLatLon(placeId: String) = homeRepositoryImp.fetchGooglePlaceOfLatLon(placeId)


    // Local
    suspend fun insertCity(city: CityModel) = homeRepositoryImp.insertCity(city)

    suspend fun getCities() = homeRepositoryImp.getCities()

    suspend fun getSizeCities() = homeRepositoryImp.getSizeCities()

    suspend fun deleteCity(id : Long) = homeRepositoryImp.deleteCity(id)

    suspend fun deleteForecastCity(id : Long) = homeRepositoryImp.deleteForecastCity(id)


}