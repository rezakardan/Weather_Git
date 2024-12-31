package com.example.weatherapp.data.repository

import com.example.weatherapp.data.db.CitiesDao
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.network.ApiServices
import javax.inject.Inject

class CitiesRepository@Inject constructor(private val dao: CitiesDao){




    fun getAllCities()=dao.getAllCities()


   suspend fun deleteCities(entity: CitiesEntity)=dao.deleteCity(entity)


}