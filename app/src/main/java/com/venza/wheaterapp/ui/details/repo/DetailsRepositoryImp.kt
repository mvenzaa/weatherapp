package com.venza.wheaterapp.ui.details.repo

import com.venza.wheaterapp.data.ResultData
import com.venza.wheaterapp.db.ForecastCityDao
import com.venza.wheaterapp.db.ForecastCityModel
import com.venza.wheaterapp.domain.api.ApiService
import com.venza.wheaterapp.domain.api.BaseDataSource

class DetailsRepositoryImp(
    private val apiService: ApiService,
    private val forecastCityDao : ForecastCityDao
) : BaseDataSource() {

    // Retrofit Api
    suspend fun findCityForecastData(city:String) = getResult {
        apiService.findCityForecastData(city)
    }

    // Local Room

    suspend fun insertForecastCity(forecastCity: ForecastCityModel) = try {
        ResultData.Success(data = forecastCityDao.insertForecastCity(forecastCity))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun getForecastCity(id : Long) = try {
        ResultData.Success(data = forecastCityDao.getForecastCity(id))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }




}