package com.example.weatherapp.data.repository

import com.example.weatherapp.data.db.CitiesDao
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.network.ApiServices
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val apiServices: ApiServices
) {





    suspend fun getAirPollution(
        appid: String,
        lat: Double,
        lon: Double

    ) = apiServices.getAirPollution(appid, lat, lon)






}