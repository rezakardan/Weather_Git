package com.example.weatherapp.data.repository

import com.example.weatherapp.data.db.CitiesDao
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.network.ApiServices
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dao: CitiesDao,
    private val apiServices: ApiServices
) {


    fun getAllCities() = dao.getAllCities()


    suspend fun getCurrentWeather(
        appid: String,
        lat: Double,
        lon: Double

    ) = apiServices.getCurrentWeather(appid, lat, lon, "fa", "metric")



    suspend fun getForecastWeather(
        appid: String,
        lat: Double,
        lon: Double

    ) = apiServices.getForecastWeather(appid, lat, lon, "fa", "metric")


}