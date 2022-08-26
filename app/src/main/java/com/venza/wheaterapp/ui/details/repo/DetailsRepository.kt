package com.venza.wheaterapp.ui.details.repo

import com.venza.wheaterapp.data.ResultData
import com.venza.wheaterapp.db.ForecastCityModel
import com.venza.wheaterapp.domain.model.forecast.ForecastCityResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsRepository(
    private val detailsRepositoryImp: DetailsRepositoryImp
) {

    // Retrofit Api
    fun findCityForecastData(city: String): Flow<ResultData<ForecastCityResponse>> = flow {
        emit(detailsRepositoryImp.findCityForecastData(city))
    }

    // Local

    suspend fun insertForecastCity(forecastCity: ForecastCityModel) = detailsRepositoryImp.insertForecastCity(forecastCity)

    suspend fun getForecastCity(id : Long) = detailsRepositoryImp.getForecastCity(id)


}