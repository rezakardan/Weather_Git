package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.main.ResponseCurrentWeather
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val getAllCitiesLiveData = MutableLiveData<List<CitiesEntity>>()

    fun callGetAllCities() = viewModelScope.launch {

        repository.getAllCities().collect {

            getAllCitiesLiveData.value = it


        }


    }


    val getCurrentWeatherLiveData = MutableLiveData<NetworkRequest<ResponseCurrentWeather>>()

    fun callGetCurrentWeather(
        appid: String,
        lat: Double,
        lon: Double,

    ) = viewModelScope.launch {

        getCurrentWeatherLiveData.value = NetworkRequest.Loading()


        val response = repository.getCurrentWeather(appid, lat, lon)

        getCurrentWeatherLiveData.value = NetworkResponse(response).generateResponse()


    }





    val getForecastWeatherLiveData = MutableLiveData<NetworkRequest<ResponseForecast>>()

    fun callGetForecast(
        appid: String,
        lat: Double,
        lon: Double,

        ) = viewModelScope.launch {

        getForecastWeatherLiveData.value = NetworkRequest.Loading()


        val response = repository.getForecastWeather(appid, lat, lon)

        getForecastWeatherLiveData.value = NetworkResponse(response).generateResponse()


    }


}