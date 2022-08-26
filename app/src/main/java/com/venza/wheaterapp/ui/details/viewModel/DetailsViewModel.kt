package com.venza.wheaterapp.ui.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.venza.wheaterapp.db.ForecastCityModel
import com.venza.wheaterapp.ui.details.repo.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {


    fun findCityForecastData(city:String) = detailsRepository.findCityForecastData(city).asLiveData()

    suspend fun insertForecastCity(forecastCity: ForecastCityModel) = detailsRepository.insertForecastCity(forecastCity)

    suspend fun getForecastCity(id : Long) = detailsRepository.getForecastCity(id)

}