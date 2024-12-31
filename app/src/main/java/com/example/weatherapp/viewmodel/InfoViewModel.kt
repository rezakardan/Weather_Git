package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.info.ResponsePollution
import com.example.weatherapp.data.model.main.ResponseCurrentWeather
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.data.repository.InfoRepository
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val repository: InfoRepository) : ViewModel() {





    val getAirPollutionLiveData = MutableLiveData<NetworkRequest<ResponsePollution>>()

    fun callAirPollution(
        appid: String,
        lat: Double,
        lon: Double,

    ) = viewModelScope.launch {

        getAirPollutionLiveData.value = NetworkRequest.Loading()


        val response = repository.getAirPollution(appid, lat, lon)

        getAirPollutionLiveData.value = NetworkResponse(response).generateResponse()


    }








}