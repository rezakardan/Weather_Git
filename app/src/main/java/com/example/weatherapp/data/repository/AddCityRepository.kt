package com.example.weatherapp.data.repository

import com.example.weatherapp.data.db.CitiesDao
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.network.ApiServices
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.CITIES_LIMIT
import javax.inject.Inject

class AddCityRepository@Inject constructor(private val apiServices: ApiServices,private val dao: CitiesDao){


    suspend fun addCity(q:String)=apiServices.getCitiesList(API_KEY,q, CITIES_LIMIT)

suspend fun saveCity(entity: CitiesEntity)=dao.saveCity(entity)


}