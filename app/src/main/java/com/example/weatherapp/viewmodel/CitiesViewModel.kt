package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.repository.CitiesRepository
import com.example.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CitiesViewModel@Inject constructor(private val repository: CitiesRepository):ViewModel() {



    val getAllCitiesLiveData=MutableLiveData<List<CitiesEntity>>()

    fun callGetAllCities()=viewModelScope.launch {

        repository.getAllCities().collect{

            getAllCitiesLiveData.value=it




        }




    }



    fun deleteCity(entity: CitiesEntity)=viewModelScope.launch {

        repository.deleteCities(entity)





    }



}